package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmMemberVO {

  private String member_id, password, name, status, zipcode, address, extra_addr, tel_no, level, email, 
                 reg_date, adv_agree, personal_agree, violation_count, member_delete_reason, deleted_admin, 
                 delete_date, nickname, file_name, sns_provider, sns_email_kakao, sns_email_naver, sns_email_google;
                 
}


