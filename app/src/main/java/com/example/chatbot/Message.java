package com.example.chatbot;

public class Message {
    String content;
    boolean isUser;

    Message(String content, boolean isUser){
        this.content = content;
        this.isUser = isUser;
    }

    public String getContent() {
        return content;
    }

    public boolean getIsUser(){
        return isUser;
    }
}
