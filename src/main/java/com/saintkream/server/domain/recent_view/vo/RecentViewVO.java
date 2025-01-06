package com.saintkream.server.domain.recent_view.vo;

import lombok.Data;

@Data
public class RecentViewVO {
    private int recentId;
    private int memberId;
    private int pwrId;
    private String viewedAt;

}
