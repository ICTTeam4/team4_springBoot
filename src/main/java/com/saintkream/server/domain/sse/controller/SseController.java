package com.saintkream.server.domain.sse.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.sse.service.SseEmitterService;
import com.saintkream.server.domain.sse.vo.NotificationVO;

// import com.saintkream.server.domain.sse.service.SseEmitterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SseController {
    @Autowired
    private SseEmitterService sseEmitterService;

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();


@GetMapping(value = "/connect/{id}", produces = "text/event-stream")
public ResponseEntity<SseEmitter> connect(@PathVariable("id") String id) {
    SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    System.out.println("emitter entered :"+id);
    emitterMap.put(id, emitter);
    // sseEmitters.add(emitter);
    emitter.onCompletion(() -> emitterMap.remove(id));  // Clean up when connection is completed
        emitter.onTimeout(() -> emitterMap.remove(id)); 
    try {
        List<NotificationVO> notiList = sseEmitterService.getNotiListById(id);
        DataVO dataVO = new DataVO();  
                // List<NotificationVO> notiList = service.getnotilistfromOne(member_id);
                dataVO.setData(notiList);
      emitter.send(SseEmitter.event()  
                    .name("connect")  
                    .data(dataVO)); 
    } catch (IOException e) {
      throw new RuntimeException(e);  
    }
    return ResponseEntity.ok(emitter);  

}

@GetMapping("/broadcast/{id}")
    public ResponseEntity<String> broadcastToUser(@PathVariable("id") String id,@RequestParam("sender_id") String sender_id,@RequestParam("pwr_id") String pwr_id,@RequestParam("title") String title,@RequestParam("file_name") String file_name) {
        SseEmitter emitter = emitterMap.get(id);
        System.out.println("broadcast 테스트중-----------------"+"id="+id+"-"+"sender_id :"+sender_id+"pwr_id:"+ pwr_id +"--"+ title +"--file_name "+file_name+"---------------------------");
        if (emitter != null) {
            executor.execute(() -> {
            try {

                NotificationVO notiVO1 = new NotificationVO();
                notiVO1.setIs_deleted("0");
                notiVO1.setPwr_id(pwr_id);
                notiVO1.setReceiver_id(id);
                notiVO1.setSender_id(sender_id);
                notiVO1.setTitle(title);
                notiVO1.setType("bookmark");
                notiVO1.setFile_name(file_name);
                int result = sseEmitterService.getNotiInsert(notiVO1);
                System.out.println("-------gggg");
                List<NotificationVO> notiList = sseEmitterService.getNotiListById(id);
                System.out.println("notiList ---:"+notiList.toString());
                DataVO dataVO = new DataVO();
                // List<NotificationVO> notiList = service.getnotilistfromOne(member_id);
                dataVO.setData(notiList);
                emitter.send(SseEmitter.event().name("update").data(dataVO.getData(), MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                emitter.complete();
                emitterMap.remove(id);
                
            }
        });
        return ResponseEntity.ok("Message sent to user: " + id);
        } else {
            return ResponseEntity.status(404).body("User not connected");
        }

    }
    @GetMapping("/deletenoti/{noti_id}")
    public ResponseEntity<String> getNotiDelete(@PathVariable("noti_id") String noti_id) {
        try {
            int result = sseEmitterService.getNotiDelete(noti_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok("Delete completed: " + noti_id);
    }
    


// @PostMapping("/broadcast/{userId}")
// public void broadcast(@PathVariable int userId, @RequestBody EventPayload ) {
    
// }



}
