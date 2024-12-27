package com.saintkream.server.domain.chatserver;

public class HelloMessage {

    private String name;
    private String message; 

    // 기본 생성자
    public HelloMessage() {}

    // Getter와 Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
