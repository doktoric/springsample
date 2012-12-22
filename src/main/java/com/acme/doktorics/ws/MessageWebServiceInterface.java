package com.acme.doktorics.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface MessageWebServiceInterface {

    @WebMethod(operationName = "sendMessageToChannel")
    boolean sendMessageToChannel(@WebParam(name = "messageText") String messageText, @WebParam(name = "messageFromPerson") String messageFromPerson);

}
