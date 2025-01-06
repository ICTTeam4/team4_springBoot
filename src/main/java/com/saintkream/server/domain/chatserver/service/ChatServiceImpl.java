package com.saintkream.server.domain.chatserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.chatserver.mapper.ChatMapper;
import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;

@Service
public class ChatServiceImpl implements ChatService  {
  @Autowired
  private ChatMapper chatMapper;
  @Override
  public ChatRoomVO getOneRoom(ChatRoomVO cvo) {
    return chatMapper.getOneRoom(cvo);
  }
  @Override
  public int getChatRoomInsert(ChatRoomVO cvo) {
    return chatMapper.getChatRoomInsert(cvo);
  }
  @Override
  public int getSelectlastInsert() {
    return chatMapper.getSelectlastInsert();
  }
  
}
