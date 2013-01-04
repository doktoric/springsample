package com.acme.doktorics.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.acme.doktorics.domain.Message;

public interface IMessageService extends ApplicationEventPublisherAware {

    final static Integer LIMIT=7;
    
	@Override
    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
	
	abstract void sendMessage(String from, String message);

	abstract void sendMessage(Message message);

	List<Message> findAll();
	
	List<Message> findAllBySortedByDate();
	
	List<Message> findFrom(int begin);

	void deleteMessage(String id);

	void saveMessage(Message messageObject);
}