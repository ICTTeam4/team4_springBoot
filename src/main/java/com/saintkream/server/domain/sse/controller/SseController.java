package com.saintkream.server.domain.sse.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.saintkream.server.domain.sse.service.SseEmitterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@Slf4j
@RequestMapping("/api")
public class SseController {
// private final SseEmitterService sseEmitterService;
  // private final List<SseEmitter> sseEmitters;
  private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
  // public SseController(List<SseEmitter> sseEmitters){
  //   this.sseEmitters = sseEmitters;

  // }

// @GetMapping(value = "/connect", produces = "text/event-stream")
// public ResponseEntity<SseEmitter> connect() {
//     SseEmitter emitter = new SseEmitter();
//     System.out.println("emitter entered");
//     sseEmitters.add(emitter);
//     try {
//       emitter.send(SseEmitter.event()  
//                     .name("connect")  
//                     .data("connected!")); 
//     } catch (IOException e) {
//       throw new RuntimeException(e);  
//     }
//     return ResponseEntity.ok(emitter);  

// }

@GetMapping(value = "/connect/{userId}", produces = "text/event-stream")
public ResponseEntity<SseEmitter> connect(@PathVariable String userId) {
    SseEmitter emitter = new SseEmitter();
    emitterMap.put(userId, emitter);
    emitter.onCompletion(() -> emitterMap.remove(userId));
    emitter.onTimeout(() -> emitterMap.remove(userId));
    return ResponseEntity.ok(emitter);
}

@PostMapping("/broadcast/{userId}")
public void broadcastToUser(@PathVariable String userId, @RequestBody String message) {
    SseEmitter emitter = emitterMap.get(userId);
    if (emitter != null) {
        try {
            emitter.send(SseEmitter.event()
                .name("personal-update")
                .data(message));
        } catch (IOException e) {
            emitter.complete();
            emitterMap.remove(userId);
        }
    }
}

// @PostMapping("/broadcast/{userId}")
// public void broadcast(@PathVariable int userId, @RequestBody EventPayload ) {
    
// }



}
