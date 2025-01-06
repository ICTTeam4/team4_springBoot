package com.saintkream.server.domain.chatserver.service;

import java.util.List;

import com.saintkream.server.domain.chatserver.vo.ChatMessageVO;
import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;

public interface ChatService {
  public ChatRoomVO getOneRoom(ChatRoomVO cvo);
  int getChatRoomInsert(ChatRoomVO cvo);
  int getSelectlastInsert();
  public List<ChatMessageVO> getMessagesByRoomId(String room_id);
   // 메시지 저장 메서드 추가
   int saveMessage(String roomId,String member_id,String content);
   int updateIsRead(String roomId, String memberId);
   
}
