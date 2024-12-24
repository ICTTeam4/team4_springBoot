package com.saintkream.server.domain.chatserver;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    System.out.println("Received Name: " + message.getName());
    System.out.println("Received Message: " + message.getMessage());
      return new Greeting(message.getName(), message.getMessage()); // name과 message를 개별적으로 반환
  }
  
}
