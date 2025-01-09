package com.saintkream.server.domain.chatserver.vo;

public class ChatMessageToClient {

    private String member_id;    // 발신자 이름
    private String content; // 메시지 내용

    // 기본 생성자
    public ChatMessageToClient() {
    }

    // 모든 필드를 포함하는 생성자
    public ChatMessageToClient(String member_id, String content) {
        this.member_id = member_id;
        this.content = content;
    }

    // Getter와 Setter
    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
