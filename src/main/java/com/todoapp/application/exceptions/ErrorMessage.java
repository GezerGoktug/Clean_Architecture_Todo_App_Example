package com.todoapp.application.exceptions;

public class ErrorMessage {

    private String offStatic;
    private MessageType messageType;

    public ErrorMessage(String offStatic, MessageType messageType) {
        this.offStatic = offStatic;
        this.messageType = messageType;
    }

    public ErrorMessage() {
    }

    public String prepareErrorMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if (offStatic != null) {
            builder.append(" : " + offStatic);
        }

        return builder.toString();
    }

    public String getOffStatic() {
        return offStatic;
    }

    public void setOffStatic(String offStatic) {
        this.offStatic = offStatic;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

}
