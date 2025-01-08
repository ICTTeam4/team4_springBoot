// package com.saintkream.server.domain.chatserver.controller;

// import org.springframework.messaging.handler.annotation.DestinationVariable;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.util.HtmlUtils;

// import com.saintkream.server.domain.chatserver.vo.ChatMessageToClient;
// import com.saintkream.server.domain.chatserver.vo.ChatMessageToServer;

// @Controller
// public class MessageController {
//   //여기로 올때
//   @MessageMapping("/chat/{roomId}")
//   //다른데로 보낼때
//   @SendTo("/topic/chat/{roomId}")
//   public ChatMessageToClient greeting(@DestinationVariable("roomId") String roomId, ChatMessageToServer message) throws Exception {
//     System.out.println("roomId :"+roomId);
//     System.out.println("Received Name: " + message.getName());
//     System.out.println("Received Message: " + message.getMessage());
//     return new ChatMessageToClient(message.getName(), message.getMessage()); // name과 message를 개별적으로 반환
//   }

//   // @GetMapping("/chat")
//   // public String redirectStatic() {
//   //   return "forward:/chat/index.html";
//   // }
// }
