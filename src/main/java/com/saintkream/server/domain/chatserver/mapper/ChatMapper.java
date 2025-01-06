package com.saintkream.server.domain.chatserver.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;

@Mapper
public interface ChatMapper {
  public ChatRoomVO getOneRoom(ChatRoomVO cvo);

  int getChatRoomInsert(ChatRoomVO cvo);

  int getSelectlastInsert();
}
