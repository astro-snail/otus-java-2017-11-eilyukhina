package ru.otus.l131.cache;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.ToLongFunction;

public class CacheStore implements Cache {

	private static final int TIME_THRESHOLD_MS = 5;
	private static final int MAX_CAPACITY = 1000;

	private final int maxCapacity;
	private final long lifeTime;
	private final long idleTime;
	private final boolean isEternal;

	private final Map<Object, Reference<Element>> elements = new LinkedHashMap<>();
	private final Timer timer = new Timer();

	private int hitCount = 0;
	private int missCount = 0;

	public CacheStore(int maxCapacity, long lifeTime, long idleTime, boolean isEternal) {
		this.maxCapacity = Math.min(maxCapacity, MAX_CAPACITY);
		this.lifeTime = Math.max(lifeTime, 0);
		this.idleTime = Math.max(idleTime, 0);
		this.isEternal = (lifeTime == 0 && idleTime == 0) || isEternal;
	}

	@Override
	public void put(Element element) {
		Object key = element.getKey();

		synchronized (elements) {
			if (elements.size() == maxCapacity) {
				Object firstKey = elements.keySet().iterator().next();
				elements.remove(firstKey);
			}
			elements.put(key, new SoftReference<>(element));
		}

		if (!isEternal) {
			scheduleEviction(key);
		}
	}

	@Override
	public Element get(Object key) {
		Element element = null;

		synchronized (elements) {
			Reference<Element> ref = elements.get(key);
			if (ref != null) {
				element = ref.get();
				if (element != null) {
					element.setLastAccessTime(getCurrentTime());
				} else {
					elements.remove(key);
				}
			}
		}
		if (element != null) {
			hitCount++;
		} else {
			missCount++;
		}
		return element;
	}

	@Override
	public boolean remove(Object key) {
		synchronized (elements) {
			return elements.remove(key) != null ? true : false;
		}
	}

	@Override
	public void dispose() {
		timer.cancel();
		synchronized (elements) {
			elements.clear();
		}
	}

	@Override
	public int getHitCount() {
		return hitCount;
	}

	@Override
	public int getMissCount() {
		return missCount;
	}

	@Override
	public int getSize() {
		synchronized (elements) {
			return elements.size();
		}
	}

	@Override
	public Properties getProperties() {
		Properties props = new Properties();
		props.put("maxCapacity", maxCapacity);
		props.put("lifeTime", lifeTime);
		props.put("idleTime", idleTime);
		props.put("isEternal", isEternal);
		return props;
	}

	private void scheduleEviction(Object key) {
		if (lifeTime != 0) {
			TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTime);
			timer.schedule(lifeTimerTask, lifeTime);
		}
		if (idleTime != 0) {
			TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTime);
			timer.schedule(idleTimerTask, idleTime, idleTime);
		}
	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}

	private TimerTask getTimerTask(Object key, ToLongFunction<Element> timeFunction) {
		return new TimerTask() {
			@Override
			public void run() {
				synchronized (elements) {
					Reference<Element> ref = elements.get(key);
					if (ref != null) {
						Element element = ref.get();
						if (element == null
								|| timeFunction.applyAsLong(element) < getCurrentTime() + TIME_THRESHOLD_MS) {
							elements.remove(key);
							this.cancel();
						}
					} else {
						this.cancel();
					}
				}
			}
		};
	}
}
