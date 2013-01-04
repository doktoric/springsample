package com.acme.doktorics.event;

import org.springframework.context.ApplicationEvent;

import com.acme.doktorics.domain.MessageEventType;
import com.acme.doktorics.domain.Message;

public class MessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8689894315996197723L;
	private Message message;
	private MessageEventType event;

	public MessageEvent(Object source, Message message,MessageEventType event) {
		super(source);
		this.message = message;
		this.event = event;
	}

	public MessageEventType getEvent() {
		return event;
	}

	public void setEventType(MessageEventType event) {
		this.event = event;
	}

	public String getDate() {
		return message.getMessageDate().toString();
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
