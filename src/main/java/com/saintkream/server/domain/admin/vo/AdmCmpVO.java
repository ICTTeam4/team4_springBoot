package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmCmpVO {
  private String report_id, member_id, reported_id, report_type, board_id, report_reason, report_detail, report_status, created_at, resolved_at;
}
