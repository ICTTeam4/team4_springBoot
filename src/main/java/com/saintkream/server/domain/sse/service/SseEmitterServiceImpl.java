package com.saintkream.server.domain.sse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.sse.mapper.SseMapper;
import com.saintkream.server.domain.sse.vo.NotificationVO;

@Service
public class SseEmitterServiceImpl implements SseEmitterService {

  @Autowired
  private SseMapper sseMapper;

  @Override
  public int getNotiInsert(NotificationVO notivo) {
    return sseMapper.getNotiInsert(notivo);
  }

  @Override
  public List<NotificationVO> getNotiListById(String member_id) {
    return sseMapper.getNotiListById(member_id);
  }

  @Override
  public int getNotiDelete(String noti_id) {
    return sseMapper.getNotiDelete(noti_id);
  }

}
