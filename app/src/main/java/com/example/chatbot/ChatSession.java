package com.example.chatbot;

import java.util.List;

public class ChatSession {
    private String id;
    private String title;
    private List<Message> messages;

    public ChatSession(String id, String title, List<Message> messages) {
        this.id = id;
        this.title = title;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
