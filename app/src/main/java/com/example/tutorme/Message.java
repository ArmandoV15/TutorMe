package com.example.tutorme;

public class Message {
    private String textMessage;
    private String userName;

    public Message(){

    }

    public Message(String textMessage, String userName){
        this.textMessage = textMessage;
        this.userName = userName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

