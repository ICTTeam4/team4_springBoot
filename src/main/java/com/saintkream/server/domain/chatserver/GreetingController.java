package com.saintkream.server.domain.chatserver;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
  //여기로 올때
  @MessageMapping("/chat/{roomId}")
  //다른데로 보낼때
  @SendTo("/topic/chat/{roomId}")
  public Greeting greeting(@DestinationVariable("roomId") String roomId, HelloMessage message) throws Exception {
    System.out.println("roomId :"+roomId);
    System.out.println("Received Name: " + message.getName());
    System.out.println("Received Message: " + message.getMessage());
    return new Greeting(message.getName(), message.getMessage()); // name과 message를 개별적으로 반환
  }

  // @GetMapping("/chat")
  // public String redirectStatic() {
  //   return "forward:/chat/index.html";
  // }
}
