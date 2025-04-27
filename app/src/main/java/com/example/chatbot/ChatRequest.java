package com.example.chatbot;

import java.util.List;

public class ChatRequest {
    private String model;
    private List<MessageData> messages;

    public ChatRequest(String model, List<MessageData> messages) {
        this.model = model;
        this.messages = messages;
    }

    public static class MessageData {
        private String role;
        private String content;

        public MessageData(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
