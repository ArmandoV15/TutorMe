package com.example.tutorme;

public class Message {
    private String textMessage;
    private String sender;
    private String receiver;

    public Message(){

    }

    public Message(String sender, String receiver, String textMessage){
        this.textMessage = textMessage;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String userName) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

