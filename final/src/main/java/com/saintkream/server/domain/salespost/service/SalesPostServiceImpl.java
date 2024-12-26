package com.saintkream.server.domain.salespost.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.salespost.mapper.SalesPostMapper;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

@Service
public class SalesPostServiceImpl implements SalesPostService{
  @Autowired
  private SalesPostMapper salesPostMapper;

  @Override
  public int getSalesPostWrite(SalesPostVO svo) {
    return salesPostMapper.getSalesPostWrite(svo);
  }

  @Override
  public int getFileWrite(Map<String, String> map) {
    return salesPostMapper.getFileWrite(map);
  }

  @Override
  public int getPostFileWrite(Map<String, String> map) {
    return salesPostMapper.getPostFileWrite(map);
  }
  
}
