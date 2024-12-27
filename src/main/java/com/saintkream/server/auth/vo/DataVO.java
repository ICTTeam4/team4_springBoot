package com.saintkream.server.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVO {
    // 클라이언트에 결과 정보를 보낼때 사용
    private boolean success;
    private Object data;
    private String token;
    private String message;
    // private UserDetails userDetails;

}
