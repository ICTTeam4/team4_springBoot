package com.saintkream.server.domain.salespost.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.salespost.vo.SalesPostVO;

@Mapper
public interface SalesPostMapper {
  int getSalesPostWrite(SalesPostVO svo);
  int getFileWrite(Map<String,String> map);
  int getPostFileWrite(Map<String,String> map);
}
