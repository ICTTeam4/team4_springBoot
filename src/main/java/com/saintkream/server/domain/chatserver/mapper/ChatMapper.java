package com.saintkream.server.domain.chatserver.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.chatserver.vo.ChatMessageVO;
import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;


@Mapper
public interface ChatMapper {
public ChatRoomVO getOneRoom(ChatRoomVO cvo);

int getChatRoomInsert(ChatRoomVO cvo);

  int getSelectlastInsert();

  public List<ChatMessageVO> getMessagesByRoomId(String room_id);

  int updateIsRead(@Param("roomId") String roomId, @Param("memberId") String memberId);

  int saveMessage(ChatMessageVO messageVO);

  public List<ChatMessageVO> getMessagesListByMember_Id(String member_id);

  public List<String> getRoomIdsByMemberId(String member_id);

  public int getRoomIdsByPwrId(int pwr_id);
}
