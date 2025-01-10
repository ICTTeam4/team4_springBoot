package com.saintkream.server.domain.sse.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationVO {
private String noti_id,pwr_id,is_deleted,type,name,title,sender_id,receiver_id,created_at,file_name;
}
