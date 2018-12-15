package ru.otus.l151.app.messages;

import java.sql.SQLException;

import javax.servlet.AsyncContext;

import ru.otus.l151.app.MsgToDB;
import ru.otus.l151.dataset.UserDataSet;
import ru.otus.l151.dbservice.DBService;
import ru.otus.l151.messagesystem.Address;
import ru.otus.l151.messagesystem.MessageException;

public class MsgDeleteUserRequest extends MsgToDB {
	private final UserDataSet user;
	private final AsyncContext asyncContext;
	
	public MsgDeleteUserRequest(Address from, Address to, AsyncContext asyncContext, UserDataSet user) {
		super(from, to);
		this.user = user;
		this.asyncContext = asyncContext;
	}

	@Override
	public void exec(DBService dbService) throws MessageException {
		try {
			dbService.delete(user);
			dbService.getMessageSystem().sendMessage(new MsgDeleteUserResponse(getTo(), getFrom(), asyncContext, "User deleted"));
		} catch (SQLException e) {
			throw new MessageException(e);
		}
	}

}