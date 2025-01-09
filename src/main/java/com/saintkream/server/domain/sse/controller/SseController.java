package com.saintkream.server.domain.sse.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

// import com.saintkream.server.domain.sse.service.SseEmitterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
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
    // @Autowired
    // private final sseEmitterService sseEmitterService;

    // private final List<SseEmitter> sseEmitters;
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

//   public SseController(List<SseEmitter> sseEmitters){
//     this.sseEmitters = sseEmitters;
//   }

@GetMapping(value = "/connect/{id}", produces = "text/event-stream")
public ResponseEntity<SseEmitter> connect(@PathVariable("id") String id) {
    SseEmitter emitter = new SseEmitter();
    System.out.println("emitter entered :"+id);
    emitterMap.put(id, emitter);
    // sseEmitters.add(emitter);
    emitter.onCompletion(() -> emitterMap.remove(id));  // Clean up when connection is completed
        emitter.onTimeout(() -> emitterMap.remove(id)); 
    try {
      emitter.send(SseEmitter.event()  
                    .name("connect")  
                    .data("connected!"+id)); 
    } catch (IOException e) {
      throw new RuntimeException(e);  
    }
    return ResponseEntity.ok(emitter);  

}

@GetMapping("/broadcast/{id}")
    public ResponseEntity<String> broadcastToUser(@PathVariable("id") String id,@RequestParam("message") String message) {
        SseEmitter emitter = emitterMap.get(id);
        System.out.println("broadcast 테스트중-----------------"+"id="+id+"-"+"message :"+message+"--------------------------------------");
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("update").data(message));
                return ResponseEntity.ok("Message sent to user: " + id);
            } catch (IOException e) {
                emitter.complete();
                emitterMap.remove(id);
                return ResponseEntity.status(500).body("Error sending message to user: " + id);
            }
        } else {
            return ResponseEntity.status(404).body("User not connected");
        }
    }



// @PostMapping("/broadcast/{userId}")
// public void broadcast(@PathVariable int userId, @RequestBody EventPayload ) {
    
// }



}
