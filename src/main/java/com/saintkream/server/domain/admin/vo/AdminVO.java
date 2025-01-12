package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVO {

  private String admin_id, admin_password, admin_name, status, tel_no, level, reg_date, delete_date;
  
}
