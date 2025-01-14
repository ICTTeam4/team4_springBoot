package com.saintkream.server.domain.chatserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import com.saintkream.server.domain.chatserver.service.ChatService;
import com.saintkream.server.domain.chatserver.vo.ChatMessageToClient;
import com.saintkream.server.domain.chatserver.vo.ChatMessageToServer;
@Controller
public class MessageController {
  @Autowired
  ChatService chatService;
  //여기로 올때
  @MessageMapping("/chat/{roomId}")
  //다른데로 보낼때
  @SendTo("/topic/chat/{roomId}")
  public ChatMessageToClient greeting(@DestinationVariable("roomId") String roomId, ChatMessageToServer message) throws Exception {
    System.out.println("roomId :"+roomId);
    System.out.println("Received member_id: " + message.getMember_id());
    System.out.println("Received Message: " + message.getContent());
    chatService.saveMessage(roomId, message.getMember_id(), message.getContent());
    return new ChatMessageToClient(message.getMember_id(), message.getContent()); // name과 message를 개별적으로 반환
  }


}
