package com.saintkream.server.domain.chatserver.controller;

import java.util.ArrayList;
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

    // 이건 채팅방에 들어왔을때
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
            // 새로 생성한 채팅방 정보를 다시 가져오기
            cvo2 = chatService.getOneRoom(cvo);
            if (cvo2 == null) {
                // 생성 후에도 채팅방 정보를 가져오지 못한 경우
                dataVo.setSuccess(false);
                dataVo.setMessage("채팅방 생성 후 정보를 가져오지 못했습니다.");
                return dataVo;
            }
        } // 공통 로직: 채팅방 메시지 가져오기
        List<ChatMessageVO> messages = chatService.getMessagesByRoomId(cvo2.getRoom_id());
        chatService.updateIsRead(cvo2.getRoom_id(), buyerId);
        dataVo.setSuccess(true);
        dataVo.setMessage(cvo2.getRoom_id()); // datavo일관성위해서 여기선 message에 룸아이디 반환.
        dataVo.setData(messages); // 메시지 리스트 반환
        System.out.println("메세지내용" + messages);
        return dataVo;
    }

    // 메세지들을 전부 줘야함. 룸 이 아니라.
    @GetMapping("/roomList")
    @ResponseBody
    public List<ChatMessageVO> getMessagesListByMember_Id(@RequestParam("member_id") String member_id) {

        System.out.println(member_id);
        List<ChatMessageVO> messages = chatService.getMessagesListByMember_Id(member_id);
        return messages; // 메시지 리스트 반환
    }

}
