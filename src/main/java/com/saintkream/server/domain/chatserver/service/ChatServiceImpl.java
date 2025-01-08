package com.saintkream.server.domain.chatserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.chatserver.mapper.ChatMapper;
import com.saintkream.server.domain.chatserver.vo.ChatMessageVO;
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
	@Override
	public List<ChatMessageVO> getMessagesByRoomId(String room_id) {
    return chatMapper.getMessagesByRoomId(room_id);
	}
  @Override
  public int saveMessage(String roomId, String member_id, String content) {
    System.out.println("여기선 룸아이디받을까요"+roomId);
    ChatMessageVO messageVO = new ChatMessageVO();
    messageVO.setRoom_id(roomId);
    messageVO.setMember_id(member_id);
    messageVO.setContent(content);
    return chatMapper.saveMessage(messageVO);
  }
  @Override
  public int updateIsRead(String roomId, String memberId) {
      return chatMapper.updateIsRead(roomId, memberId);
  }
  @Override
  public List<ChatMessageVO> getMessagesListByMember_Id(String member_id) {
    return chatMapper.getMessagesListByMember_Id(member_id);  }


  }