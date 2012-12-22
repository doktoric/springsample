package com.acme.doktorics.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="messages")
public class Message {

	@Id
	@GeneratedValue
	private long id;
	private String messageDate;
	private String messageText;
	private String messageFromPerson;

	
	
	public Message() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageFromPerson() {
		return messageFromPerson;
	}

	public void setMessageFromPerson(String messageFromPerson) {
		this.messageFromPerson = messageFromPerson;
	}

}
