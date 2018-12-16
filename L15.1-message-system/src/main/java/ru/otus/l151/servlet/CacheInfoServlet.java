package ru.otus.l151.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.otus.l151.messagesystem.MessageContext;
import ru.otus.l151.messagesystem.MessageEvent;
import ru.otus.l151.messagesystem.MessageEventListener;
import ru.otus.l151.uiservice.UIService;

@SuppressWarnings("serial")
public class CacheInfoServlet extends HttpServlet {
	
	@Autowired
	private UIService uiService;
	
	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (LoginServlet.checkLoggedIn(request, response)) {
			
			AsyncContext asyncContext = request.startAsync(request, response);			
			
			MessageEventListener listener = new MessageEventListener() {
				
				@Override
				public void messageReceived(MessageEvent event) {
					asyncContext.getRequest().setAttribute("cacheParameters", event.getValue());
					asyncContext.dispatch("/cache-info.jsp");
				}
			};
			
			MessageContext messageContext = new MessageContext();
			messageContext.addListener(listener);
			uiService.handleCacheRequest(messageContext);
		}
	}	
}
