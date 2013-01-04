package com.acme.doktorics.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.acme.doktorics.domain.Message;
import com.acme.doktorics.domain.WrappedMessage;
import com.acme.doktorics.service.IMessageService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    @Autowired
    private IMessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);
        model.addAttribute("messages", messageService.findFrom(0));

        return "home";
    }
    
    @ResponseBody
    @RequestMapping(value = "/messages/more/{piece}", method = RequestMethod.POST)
    public Collection<WrappedMessage> loadMoreMessages(@PathVariable int piece) throws InterruptedException {
        logger.info("AJAX calling: "+piece);
        Collection<Message> messagesByChannel = messageService.findFrom(piece);
        htmlEscapeMessages(messagesByChannel);
        return wrapMessages(messagesByChannel);
    }

    private void htmlEscapeMessages(Collection<Message> messagesByChannel) {
        for (Message message : messagesByChannel) {
            message.setMessageText(HtmlUtils.htmlEscape(message.getMessageText()));
            message.setMessageFromPerson(HtmlUtils.htmlEscape(message.getMessageFromPerson()));
            message.setMessageDate(message.getMessageDate());
        }
    }
    
    private Collection<WrappedMessage> wrapMessages(Collection<Message> messagesByChannel) {
        Collection<WrappedMessage> wrappedMessages = new ArrayList<WrappedMessage>();
        for (Message msg : messagesByChannel) {
            wrappedMessages.add(new WrappedMessage(msg));
        }
        return wrappedMessages;
    }
  

}
