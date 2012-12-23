package com.acme.doktorics.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.acme.doktorics.domain.Message;

public interface IMessageService extends ApplicationEventPublisherAware {

	void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
	
	abstract void sendMessage(String from, String message);

	abstract void sendMessage(Message message);

	List<Message> getAll();

	void deleteMessage(String id);

}