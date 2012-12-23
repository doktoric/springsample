package com.acme.doktorics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.doktorics.dao.IMessageDao;
import com.acme.doktorics.domain.Message;
import com.acme.doktorics.event.MessageEvent;

@Service
@Transactional
public class MessageService implements ApplicationEventPublisherAware{

	@Autowired IMessageDao messageDao;	
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void sendMessage(String from, String message) {
    	
        Message messageObject=new Message();
        messageObject.setMessageFromPerson(from);
        messageObject.setMessageText(message);
        messageObject.setMessageDate((new Date()).toString());
        messageDao.save(messageObject);
        publishEvent(new MessageEvent(this, messageObject));
    }

    public void sendMessage(Message message) {
        publishEvent(new MessageEvent(this, message));
    }


    private void publishEvent(MessageEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    
    public List<Message> getAll()
    {
    	List<Message> messages= messageDao.findAll();
    	if(messages==null) {
    		messages=new ArrayList<Message>();
    	}
    	return messages;
    }

}