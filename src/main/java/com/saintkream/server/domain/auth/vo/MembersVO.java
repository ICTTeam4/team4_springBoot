package com.saintkream.server.domain.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembersVO {
    private String member_id, name,email,tel_no,password,adv_agree,personal_agree,reg_date,nickname, sns_email_naver, sns_email_kakao,sns_email_google, sns_provider,profile_image;

     // 비밀번호 확인용 필드 (데이터베이스 제외)
     private transient String confirmPassword; // 데이터베이스에 저장되지 않음 yb
           // 추가된 status 필드
    private String status = "ACTIVE"; // 기본값 설정 yb


    

}