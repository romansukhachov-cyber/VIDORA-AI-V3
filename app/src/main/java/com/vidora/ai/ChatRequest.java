package com.vidora.ai;
import java.util.Collections;
import java.util.List;

public class ChatRequest {
    public String model = "gpt-3.5-turbo";
    public List<Message> messages;

    public ChatRequest(String prompt) {
        this.messages = Collections.singletonList(new Message("user", prompt));
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
