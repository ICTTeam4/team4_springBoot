package com.saintkream.server.domain.chatserver.service;

import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;

public interface ChatService {
  public ChatRoomVO getOneRoom(ChatRoomVO cvo);
  int getChatRoomInsert(ChatRoomVO cvo);
  int getSelectlastInsert();
}
