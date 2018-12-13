package ru.otus.l151.app.messages;

import ru.otus.l151.app.UIService;
import ru.otus.l151.dataset.UserDataSet;

import javax.servlet.AsyncContext;

import ru.otus.l151.app.MsgToUI;
import ru.otus.l151.messagesystem.Address;

public class MsgAddUserResponse extends MsgToUI {
	private final UserDataSet user;
	private final AsyncContext asyncContext;
	
	public MsgAddUserResponse(Address from, Address to, AsyncContext asyncContext, UserDataSet user) {
		super(from, to);
		this.user = user;
		this.asyncContext = asyncContext;
	}

	public void exec(UIService uiService) {
		uiService.receiveUser(asyncContext, user);
	}
}
