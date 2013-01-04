package com.acme.doktorics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.doktorics.dao.IMessageDao;
import com.acme.doktorics.domain.Message;
import com.acme.doktorics.domain.MessageEventType;
import com.acme.doktorics.event.MessageEvent;


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
        messageObject.setMessageDate((new Date()).toString());
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
    @Cacheable("allmessage")
    public List<Message> findAll()
    {
        logger.info("Without cache...(Update cache START)");
        List<Message> messages = messageDao.findAll();
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        logger.info("Without cache...(Update cache END)");
        return messages;
    }

    @Override
    @CacheEvict(value = "allmessage", allEntries = true)
    public void deleteMessage(String id) {
        Long itemId = Long.parseLong(id);
        Message message = messageDao.findOne(itemId);
        messageDao.delete(message);
        publishEvent(new MessageEvent(this, message, MessageEventType.DELETE));
    }

    @Override
    @CacheEvict(value = "allmessage", allEntries = true)
    public void saveMessage(Message messageObject) {
        messageDao.save(messageObject);
    }

    @Override
    public List<Message> findFrom(int begin) {
        List<Message> messages = findAll();
        List<Message> resultList=messages;
        if(messages.size()>begin+LIMIT){
            resultList=new  ArrayList<Message>();
            resultList = messages.subList(begin, begin+LIMIT);
        }
        else if(begin >= messages.size() ) {
            resultList=new  ArrayList<Message>();
        }
        else if(begin+LIMIT > messages.size() && begin< messages.size()){
            resultList=new  ArrayList<Message>();
            resultList = messages.subList(begin, messages.size());
        }
        return resultList;
    }

}