package com.acme.doktorics.service;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.doktorics.annotations.CheckMessage;

@Service
@RemoteProxy(name = "DwrService")
public class DwrService {

    @Autowired
    private IMessageService messageService;

    @RemoteMethod
    @CheckMessage
    public void sendMessage(String from, String message) {
    	messageService.sendMessage(from, message);
    }
    
    @RemoteMethod
    @CheckMessage
    public void deleteMessage(String id) {
    	messageService.deleteMessage(id);
    }

  

}
