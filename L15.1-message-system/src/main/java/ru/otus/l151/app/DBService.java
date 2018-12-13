package ru.otus.l151.app;

import java.sql.SQLException;
import java.util.List;

import ru.otus.l151.dataset.*;
import ru.otus.l151.messagesystem.Addressee;

public interface DBService extends Addressee {
	
	void save(UserDataSet user) throws SQLException;

	UserDataSet load(Long id) throws SQLException;
	
	AddressDataSet loadAddressById(Long id) throws SQLException;
	
	PhoneDataSet loadPhoneById(Long id) throws SQLException;
	
	UserDataSet loadByName(String name) throws SQLException;
	
	List<UserDataSet> loadAll() throws SQLException;
	
	AddressDataSet loadAddressByUserId(Long userId) throws SQLException;
	
	List<PhoneDataSet> loadPhonesByUserId(Long userId) throws SQLException;
	
	void delete(UserDataSet user) throws SQLException;
	
	void shutdown() throws SQLException;
}