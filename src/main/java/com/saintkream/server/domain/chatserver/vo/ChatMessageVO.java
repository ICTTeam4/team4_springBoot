package com.saintkream.server.domain.chatserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVO {
private String message_id,room_id,content,created_at,has_file,is_read,member_id,title;
}
