package com.acme.doktorics.event;

import org.springframework.context.ApplicationEvent;

import com.acme.doktorics.domain.Message;

public class MessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8689894315996197723L;
	private Message message;

	public MessageEvent(Object source, Message message) {
		super(source);
		this.message = message;
	}

	public String getDate() {
		return message.getMessageDate();
	}

	public String getTextMessage() {
		return message.getMessageText();
	}

	public Message getMessage() {
		return message;
	}
	
	public Long getId() {
		return message.getId();
	}

	public String getFrom() {
		return message.getMessageFromPerson();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageEvent [message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
