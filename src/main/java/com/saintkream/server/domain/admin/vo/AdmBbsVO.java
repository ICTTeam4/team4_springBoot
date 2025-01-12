package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmBbsVO {
  private String cs_id, cs_type, cs_subject, cs_content, cs_view_count, cs_status, cs_faq_priority, cs_notice_is_pinned, cs_crt_dt, cs_upd_dt, admin_id;
}


