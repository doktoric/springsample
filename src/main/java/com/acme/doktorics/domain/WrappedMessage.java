package com.acme.doktorics.domain;


public class WrappedMessage {

    private String message;
    private String date;
    private String objectId;
    private String from;

    public WrappedMessage(Message message) {
        setMessage(message.getMessageText());
        setDate(message.getMessageDate().toString());
        setObjectId(String.valueOf(message.getId()));
        setFrom(message.getMessageFromPerson());
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getFrom() {
        return from;
    }

}
