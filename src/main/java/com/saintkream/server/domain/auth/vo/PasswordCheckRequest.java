package com.saintkream.server.domain.auth.vo;

import lombok.Data;

@Data
public class PasswordCheckRequest {
    private String email;       // 이메일
    private String oldPassword; // 이전 비밀번호
}
