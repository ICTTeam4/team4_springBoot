package com.saintkream.server.domain.sse.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationVO {
private String noti_id,pwr_id,member_id,is_read,type;
}
