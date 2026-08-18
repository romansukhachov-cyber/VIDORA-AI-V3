package com.vidora.ai;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {

    public String model;
    public List<Message> messages;

    public ChatRequest(String prompt) {
        model = "gpt-4o-mini";

        messages = new ArrayList<>();
        messages.add(new Message("user", prompt));
    }

    public static class Message {

        public String role;
        public String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
