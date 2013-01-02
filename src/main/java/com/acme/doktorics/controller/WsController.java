package com.acme.doktorics.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.acme.doktorics.dto.MessageDto;
import com.acme.doktorics.exceptions.IllegalWsException;
import com.acme.doktorics.ws.MessageWebServiceInterface;

@Controller
public class WsController {

	@Resource(name = "wsClient")
	private MessageWebServiceInterface messageWebServiceInterface;

	@RequestMapping(value = "/webservice/send", method = RequestMethod.POST)
	public String sendMessageViaJAXWS(MessageDto message) {
		messageWebServiceInterface.sendMessageToChannel(
				message.getMessageText(), message.getMessageFromPerson());
		return "redirect:/";
	}
	
	  @ExceptionHandler(IllegalWsException.class)
	    public ModelAndView handleIllegalMessageException(Exception exception) {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("error");
	        modelAndView.addObject("errorMessage", exception.getMessage());
	        return modelAndView;
	    }

}