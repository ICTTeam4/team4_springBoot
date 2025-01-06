package com.saintkream.server.domain.chatserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomVO {
  private String room_id,pwr_id,buyer_id;
}
