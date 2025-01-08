// package com.saintkream.server.domain.chatserver.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.saintkream.server.domain.auth.vo.DataVO;
// import com.saintkream.server.domain.chatserver.service.ChatService;
// import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;


// @Controller
// @RequestMapping("/api/chat")
// public class ChatController {

//   @Autowired
//   public ChatService chatService;

//   @GetMapping("/room")
//   public DataVO getChatRoomId(@RequestBody ChatRoomVO cvo
//   ) {
//       DataVO dataVo = new DataVO();
//       // 만약 채팅방이 없다면 새로 생성하고 chatroomvo를 리턴
//         ChatRoomVO cvo2 = chatService.getOneRoom(cvo);
//       if(cvo2 == null){
//         int result = chatService.getChatRoomInsert(cvo);
//         if (result == 0) {
//           dataVo.setSuccess(false);
//           dataVo.setMessage("채팅방 생성 오류");
//           return dataVo;
//         }


//       }
//       // 만약 채팅방이 있다면 기존 chatroomvo를 리턴


//       return dataVo;
//   }
  
// }
