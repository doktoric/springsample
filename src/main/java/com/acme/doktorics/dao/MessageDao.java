package com.acme.doktorics.dao;

import org.springframework.stereotype.Repository;

import com.acme.doktorics.domain.Message;

@Repository
public class MessageDao extends AbstractJpaDAO<Message> implements IMessageDao{
		
	public MessageDao()
	{
		setClazz(Message.class);
	}

	

}
