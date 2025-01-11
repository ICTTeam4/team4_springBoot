package com.saintkream.server.domain.sse.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.sse.vo.NotificationVO;

@Mapper
public interface SseMapper {
  int getNotiInsert(NotificationVO notiVO);
  List<NotificationVO> getNotiListById(String member_id);
  int getNotiDelete(String noti_id);
}
