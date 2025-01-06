package com.saintkream.server.domain.chatserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.chatserver.service.ChatService;
import com.saintkream.server.domain.chatserver.vo.ChatMessageVO;
import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  @Autowired
  public ChatService chatService;
  @GetMapping("/room")
  @ResponseBody
  public DataVO getChatRoomId(
      @RequestParam("buyer_id") String buyerId,
      @RequestParam("pwr_id") String pwrId) {
  
      DataVO dataVo = new DataVO();
  
      // ChatRoomVO 객체 생성
      ChatRoomVO cvo = new ChatRoomVO();
      cvo.setBuyer_id(buyerId);
      cvo.setPwr_id(pwrId);
  
      // 만약 채팅방이 없다면 새로 생성하고 chatroomvo를 리턴
      ChatRoomVO cvo2 = chatService.getOneRoom(cvo);
      System.out.println("하윤서치cvo2: " + cvo2);
  
     if (cvo2 == null) {
          // 채팅방이 없으면 새로 생성
          int result = chatService.getChatRoomInsert(cvo);
          if (result == 0) {
              dataVo.setSuccess(false);
              dataVo.setMessage("채팅방 생성 오류");
              return dataVo;
          }
      } else {
          // 채팅방이 있으면 메시지 불러오기
          
          List<ChatMessageVO> messages = chatService.getMessagesByRoomId(cvo2.getRoom_id());
          chatService.updateIsRead(cvo2.getRoom_id(), buyerId);
          dataVo.setSuccess(true);
          dataVo.setMessage("채팅방과 메시지 정보 가져오기 성공");
          dataVo.setData(messages); // 메시지 리스트 반환
          System.out.println(messages);
          return dataVo;
      }
  
      dataVo.setSuccess(true);
      dataVo.setMessage("채팅방 생성 완료");
      return dataVo;
  }

}
