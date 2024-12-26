package com.saintkream.server.domain.salespost.service;

import java.util.Map;

import com.saintkream.server.domain.salespost.vo.SalesPostVO;

public interface SalesPostService {
  int getSalesPostWrite(SalesPostVO svo);
  int getFileWrite(Map<String,String> map);
  int getPostFileWrite(Map<String,String> map);
}
