package com.example.tutorme;

public class Message {
    private String textMessage;
    private String sender;
    private String receiver;

    /**
     DVC
     */
    public Message(){

    }

    /**
     EVC
     * @param sender ID of the person sending the message
     * @param receiver ID of the person receiving the message
     * @param textMessage The content of the string
     */
    public Message(String sender, String receiver, String textMessage){
        this.textMessage = textMessage;
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     toString used to display the message sent by the current user
     * @return
     */
    @Override
    public String toString() {
        return textMessage;
    }

    /**
     Getter used to get the textMessage sent
     * @return teh content of textMessage
     */
    public String getTextMessage() {
        return textMessage;
    }

    /**
     Setter used to set the textMessage
     * @param textMessage desired textMessage
     */
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    /**
     Getter used to get the ID of the sender
     * @return the content of sender
     */
    public String getSender() {
        return sender;
    }

    /**
     Setter used to set the sender ID
     * @param sender desired sender ID
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     Getter used to get the ID of the receiver
     * @return the content of receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     Setter used to set the receiver ID
     * @param receiver desired receiver ID
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

