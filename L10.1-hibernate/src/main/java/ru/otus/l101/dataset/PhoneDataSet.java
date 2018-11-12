package ru.otus.l101.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {

	private String number;
	private UserDataSet user;
	
	public PhoneDataSet() {
		super();
	}
	
	public PhoneDataSet(Long id, String number) {
		this.setId(id);
		this.setNumber(number);
	}
	
	public PhoneDataSet(String number) {
		this(null, number);
	}
	
	@Column(name = "number")
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	public UserDataSet getUser() {
		return user;
	}

	public void setUser(UserDataSet user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Phone ID = " + getId() + ", number = " + getNumber();
	}
}
