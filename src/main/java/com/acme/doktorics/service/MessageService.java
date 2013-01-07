package com.acme.doktorics.service;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.doktorics.dao.IMessageDao;
import com.acme.doktorics.domain.Message;
import com.acme.doktorics.domain.MessageEventType;
import com.acme.doktorics.event.MessageEvent;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;


@Service
@Transactional
public class MessageService implements IMessageService {

    @Autowired
    private IMessageDao messageDao;
    private ApplicationEventPublisher applicationEventPublisher;
    protected static final Logger logger = LoggerFactory.getLogger(MessageService.class);
      
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void sendMessage(String from, String message) {

        Message messageObject = new Message();
        messageObject.setMessageFromPerson(from);
        messageObject.setMessageText(message);
        messageObject.setMessageDate((new Date()));
        saveMessage(messageObject);
        publishEvent(new MessageEvent(this, messageObject, MessageEventType.SEND));
    }

    @Override
    public void sendMessage(Message message) {
        publishEvent(new MessageEvent(this, message, MessageEventType.SEND));
    }

    private void publishEvent(MessageEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    @Cacheable(cacheName="messages")
    public List<Message> findAll()
    {
       
        List<Message> messages = messageDao.findAll();
        logger.info("Cache is empty");
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        return messages;
    }

    @Override
    @TriggersRemove(cacheName="messages")
    public void deleteMessage(String id) {
        Long itemId = Long.parseLong(id);
        Message message = messageDao.findOne(itemId);
        messageDao.delete(message);
        publishEvent(new MessageEvent(this, message, MessageEventType.DELETE));
    }

    @Override
    @TriggersRemove(cacheName="messages")
    public void saveMessage(Message messageObject) {
        messageDao.save(messageObject);
    }

    @Override
    public List<Message> findAllBySortedByDate()
    {
        List<Message> messages = findAll();
        List<Message> sorted = sort(messages,on(Message.class).getMessageDate(),java.util.Collections.reverseOrder());
        return sorted;
    }
    
    
 

    @Override
    public List<Message> findFrom(int begin) {
        List<Message> messages = findAllBySortedByDate();
        List<Message> resultList=messages;
        if(messages.size()>=begin)
        {
            if(begin+LIMIT>messages.size()){
                resultList = messages.subList(begin, messages.size());
            }else{
                resultList = messages.subList(begin, begin+LIMIT);
            }
        }
        return resultList;
    }

}