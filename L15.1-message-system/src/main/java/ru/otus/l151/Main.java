package ru.otus.l151;

import java.sql.SQLException;

import ru.otus.l151.app.DBService;
import ru.otus.l151.app.MessageSystemContext;
import ru.otus.l151.app.UIService;
import ru.otus.l151.cache.CacheConfiguration;
import ru.otus.l151.cache.CacheFactory;
import ru.otus.l151.dataset.AddressDataSet;
import ru.otus.l151.dataset.PhoneDataSet;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.dbservice.DBServiceHibernateImpl;
import ru.otus.l151.messagesystem.Address;
import ru.otus.l151.messagesystem.MessageSystem;
import ru.otus.l151.servlet.UIServiceImpl;

public class Main {
	
	public static void main(String[] args) throws InterruptedException, SQLException {
		/*MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        
        Address uiAddress = new Address("Frontend");
        context.setUIAddress(uiAddress);
        
        Address dbAddress = new Address("DB");
        context.setDBAddress(dbAddress);

        UIService uiService = new UIServiceImpl(context);
        uiService.init();

        DBService dbService = new DBServiceHibernateImpl(context, CacheFactory.getCache(new CacheConfiguration()));
        dbService.init();

        messageSystem.start();
        
        UserDataSet user = new UserDataSet("Test User 1", 25);
        user.setAddress(new AddressDataSet("Green Lane"));
        user.addPhone(new PhoneDataSet("1234567890"));
        
       	uiService.saveUser(user);
       	
       	user = new UserDataSet("Test User 2", 35);
        user.setAddress(new AddressDataSet("New Street"));
        user.addPhone(new PhoneDataSet("0987654321"));
        
       	uiService.saveUser(user);
       	
       	Thread.sleep(1000);
       	
        uiService.requestUser(1L);
        uiService.requestUser("Test User 2");
        uiService.requestAllUsers();

        Thread.sleep(1000);
        messageSystem.dispose();
        
        dbService.shutdown();*/
	}
}
