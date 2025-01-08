package com.saintkream.server.domain.chatserver.vo;

public class ChatMessageToServer {

    private String member_id;
    private String content; 

    // 기본 생성자
    public ChatMessageToServer() {}

    // Getter와 Setter
    public String getMember_id() {
        return member_id;
    }

    public void getMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
