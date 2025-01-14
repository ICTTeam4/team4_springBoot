package com.saintkream.server.domain.sse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saintkream.server.domain.sse.vo.NotificationVO;


public interface SseEmitterService {
  int getNotiInsert(NotificationVO notivo);
  List<NotificationVO> getNotiListById(String member_id);
  int getNotiDelete(String noti_id);
}
