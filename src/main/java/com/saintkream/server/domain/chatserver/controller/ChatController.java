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

    @GetMapping("/getRoomTitle")
    @ResponseBody
    public String getRoomTitle(@RequestParam("room_id") String room_id) {
        System.out.println("최종룸아이디테스트: " + room_id);
    
        // Step 1: room_id로 message_rooms 테이블에서 pwr_id 검색
        String pwrIdQuery = "SELECT pwr_id FROM message_rooms WHERE room_id = ?";
        String pwrId = jdbcTemplate.queryForObject(pwrIdQuery, new Object[]{room_id}, String.class);
    
        if (pwrId == null) {
            return "pwr_id not found"; // pwr_id가 없으면 에러 메시지 반환
        }
    
        // Step 2: pwr_id로 post 테이블에서 title 검색
        String postTitleQuery = "SELECT title FROM post WHERE pwr_id = ?";
        String title = jdbcTemplate.queryForObject(postTitleQuery, new Object[]{pwrId}, String.class);
    
        return title; // 단일 title 반환
    }
        //사진가져오기 (messagerooms-post-postfiles-filetable...)
    @GetMapping("/getPostPhoto")
    public Map<String, Object> getPostPhoto(@RequestParam("room_id") String room_id) {
        System.out.println("사진가져오기실행은되는가?");
        try {
            // Step 1: room_id로 pwr_id 가져오기
            String getPwrIdQuery = "SELECT pwr_id FROM message_rooms WHERE room_id = ?";
            String pwrId = jdbcTemplate.queryForObject(getPwrIdQuery, new Object[]{room_id}, String.class);

            if (pwrId == null) {
                return Map.of("success", false, "photoUrl", "", "message", "pwr_id를 찾을 수 없습니다.");
            }

            // Step 2: pwr_id로 post_files에서 file_id 가져오기
            String getFileIdQuery = "SELECT file_id FROM post_files WHERE pwr_id = ?";
            String fileId = jdbcTemplate.queryForObject(getFileIdQuery, new Object[]{pwrId}, String.class);

            if (fileId == null) {
                return Map.of("success", false, "photoUrl", "", "message", "file_id를 찾을 수 없습니다.");
            }

            // Step 3: file_id로 file_table에서 file_name 가져오기
            String getFileNameQuery = "SELECT file_name FROM file_table WHERE file_id = ?";
            String fileName = jdbcTemplate.queryForObject(getFileNameQuery, new Object[]{fileId}, String.class);
            System.out.println("하윤파일이름"+fileName);
            if (fileName == null) {
                return Map.of("success", false, "photoUrl", "", "message", "file_name을 찾을 수 없습니다.");
            }

            // 최종 결과 반환
            return Map.of("success", true, "photoUrl", fileName);

         

        } catch (Exception e) {
            // 예외 처리
            return Map.of("success", false, "photoUrl", "", "message", "오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    @GetMapping("/getHostName")
    public Map<String, Object> getHostName(@RequestParam("room_id") String room_id) {
        System.out.println("판매자 정보 찾기 실행");
        Map<String, Object> response = new HashMap<>();
        try {
            // Step 1: room_id로 pwr_id 가져오기
            String pwrIdQuery = "SELECT pwr_id FROM message_rooms WHERE room_id = ?";
            String pwrId = jdbcTemplate.queryForObject(pwrIdQuery, new Object[]{room_id}, String.class);
    
            if (pwrId == null) {
                response.put("success", false);
                response.put("hostname", "");
                response.put("profile_image", "");
                response.put("price", null);
                response.put("message", "pwr_id not found for room_id: " + room_id);
                return response;
            }
    
            // Step 2: pwr_id로 sell_price 가져오기
            String sellPriceQuery = "SELECT sell_price FROM post WHERE pwr_id = ?";
            Double sellPrice = jdbcTemplate.queryForObject(sellPriceQuery, new Object[]{pwrId}, Double.class);
    
            // Step 3: room_id로 seller_id 가져오기
            String sellerIdQuery = "SELECT seller_id FROM message_rooms WHERE room_id = ?";
            String sellerId = jdbcTemplate.queryForObject(sellerIdQuery, new Object[]{room_id}, String.class);
    
            if (sellerId == null) {
                response.put("success", false);
                response.put("hostname", "");
                response.put("profile_image", "");
                response.put("price", sellPrice);
                response.put("message", "seller_id not found for room_id: " + room_id);
                return response;
            }
    
            // Step 4: seller_id로 nickname과 profile_image 가져오기
            String hostInfoQuery = "SELECT nickname, profile_image FROM members WHERE member_id = ?";
            Map<String, Object> hostInfo = jdbcTemplate.queryForMap(hostInfoQuery, sellerId);
    
            if (hostInfo == null || !hostInfo.containsKey("nickname") || !hostInfo.containsKey("profile_image")) {
                response.put("success", false);
                response.put("hostname", "");
                response.put("profile_image", "");
                response.put("price", sellPrice);
                response.put("message", "host information not found for seller_id: " + sellerId);
                return response;
            }
    
            response.put("success", true);
            response.put("hostname", hostInfo.get("nickname"));
            response.put("profile_image", hostInfo.get("profile_image"));
            response.put("price", sellPrice);
        } catch (Exception e) {
            response.put("success", false);
            response.put("hostname", "");
            response.put("profile_image", "");
            response.put("price", null);
            response.put("message", "Error occurred: " + e.getMessage());
        }
        System.out.println("판매자 정보 찾기 결과: " + response);
        return response;
    }
     // 사진보내기
    @PostMapping("/mediaUpload")
    public List<String> uploadMedia(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("roomId") String roomId,
            @RequestParam("memberId") String memberId) {

        List<String> fileUrls = new ArrayList<>();
        String staticPath = new File("src/main/resources/static/images").getAbsolutePath();

        // 디렉토리 생성 확인
        Path directory = Paths.get(staticPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create directory: " + staticPath);
            }
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // UUID를 사용하여 고유한 파일 이름 생성
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = directory.resolve(uniqueFileName);

                try {
                    file.transferTo(filePath.toFile());

                    // URL로 반환
                    String fileUrl = "/images/" + uniqueFileName;
                    fileUrls.add(fileUrl);

                    // 메시지 DB 저장
                    ChatMessageVO message = new ChatMessageVO();
                    message.setRoom_id(roomId);
                    message.setMember_id(memberId);
                    message.setContent(fileUrl);
                    message.setHas_file("1");
                    chatService.saveMedia(message);

                        System.err.println("찐마지막 사진체크 ");
                   
                    ChatMessageToClient webSocketMessage = new ChatMessageToClient(memberId, fileUrl);
                    messagingTemplate.convertAndSend("/topic/chat/" + roomId, webSocketMessage);

                    System.out.println("저장된 파일 경로: " + filePath.toAbsolutePath());
                    System.out.println("파일 URL: " + fileUrl);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileUrls;
    }

} 



