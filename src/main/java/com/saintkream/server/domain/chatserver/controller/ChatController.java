package com.saintkream.server.domain.chatserver.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.chatserver.service.ChatService;
import com.saintkream.server.domain.chatserver.vo.ChatMessageToClient;
import com.saintkream.server.domain.chatserver.vo.ChatMessageVO;
import com.saintkream.server.domain.chatserver.vo.ChatRoomVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    public ChatService chatService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

      @Autowired
    private SimpMessagingTemplate messagingTemplate; // WebSocket 메시지 전송을 위한 Bean 주입

    // 이건 채팅방에 들어왔을때
    @GetMapping("/room")
    @ResponseBody
    public DataVO getChatRoomId(
            @RequestParam("seller_id") String sellerId,
            @RequestParam("buyer_id") String buyerId,
            @RequestParam("pwr_id") String pwrId) {

        DataVO dataVo = new DataVO();

        // ChatRoomVO 객체 생성
        ChatRoomVO cvo = new ChatRoomVO();
        cvo.setSeller_id(sellerId);
        cvo.setBuyer_id(buyerId);
        cvo.setPwr_id(pwrId);

        // 만약 채팅방이 없다면 새로 생성하고 chatroomvo를 리턴
        ChatRoomVO cvo2 = chatService.getOneRoom(cvo);
        System.out.println("하윤서치cvo2: " + cvo2);

        if (cvo2 == null) {
            // 채팅방이 없으면 새로 생성
            int result = chatService.getChatRoomInsert(cvo);
            // 그래도 실패시
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

    // 메세지들을 전부 줘야함. 룸 이 아니라......
    @GetMapping("/roomList")
    @ResponseBody
    public List<ChatMessageVO> getMessagesListByMember_Id(@RequestParam("member_id") String member_id) {
        System.out.println("요청 받은 member_id: " + member_id);

        // 1. buyer_id 또는 seller_id가 member_id와 일치하는 room_id 가져오기
        List<String> roomIds = chatService.getRoomIdsByMemberId(member_id);

        // 2. 각 room_id에 해당하는 메시지 가져오기
        List<ChatMessageVO> messages = new ArrayList<>();
        for (String roomId : roomIds) {
            List<ChatMessageVO> roomMessages = chatService.getMessagesByRoomId(roomId);
            messages.addAll(roomMessages);
        }

        return messages; // 모든 메시지 리스트 반환
    }

    // 메세지리스트 가져오기. 판매자 ,구매자 포함..... 위는 잘못되었음...
    @GetMapping("/messageListForAll")
    @ResponseBody
    public List<ChatMessageVO> messageListForAll(@RequestParam("room_id") String room_id) {

        System.out.println("최종룸아이디테스트" + room_id);
        List<ChatMessageVO> messages = chatService.getMessagesByRoomId(room_id);
        return messages; // 메시지 리스트 반환
    }

    @GetMapping("/getroomidsbypwrid")
    public DataVO getRoomIdsByPwrId(@RequestParam("pwr_id") String pwrId) {
    DataVO dataVO = new DataVO();
    int pwr_id = Integer.parseInt(pwrId);
    try {
      int result = chatService.getRoomIdsByPwrId(pwr_id);
      log.info("------------");
      log.info("crvo:", result);
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(result);
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

}
