package com.saintkream.server.domain.report.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReportVO {
    private String report_id;
    private String member_id;         // 신고자 ID
    private String board_id;           // 신고 대상 게시물 ID
    private String report_reason;  // 신고 사유
    // private String created_at;
}
