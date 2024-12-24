package com.saintkream.server.domain.chatserver;

public class Greeting {

    private String name;    // 발신자 이름
    private String message; // 메시지 내용

    // 기본 생성자
    public Greeting() {
    }

    // 모든 필드를 포함하는 생성자
    public Greeting(String name, String message) {
        this.name = name;
        this.message = message;
    }

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
