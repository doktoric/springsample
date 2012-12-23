package com.acme.doktorics.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acme.doktorics.service.IMessageService;

@Component
@WebService
public class MessageWebService implements MessageWebServiceInterface {

	
	@Autowired   private IMessageService messageService;
	
	@Override
	@WebMethod(operationName = "sendMessageToChannel")
	public boolean sendMessageToChannel(String messageText,
			String messageFromPerson) {
		
		messageService.sendMessage(messageFromPerson,messageText);
		return true;
	}

}
