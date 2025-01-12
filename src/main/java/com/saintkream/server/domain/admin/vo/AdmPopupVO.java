package com.saintkream.server.domain.admin.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmPopupVO {
   private String pu_id, pu_title, pu_start_time, pu_end_time, pu_disable_hours, 
                  pu_x, pu_y, pu_height, pu_width, pu_subject, pu_content, crt_by, crt_dt, upd_by, upd_dt, f_name ;
   private MultipartFile file;
}
