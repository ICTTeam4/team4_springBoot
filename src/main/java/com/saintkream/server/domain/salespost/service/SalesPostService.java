package com.saintkream.server.domain.salespost.service;

import java.util.List;
import java.util.Map;

import com.saintkream.server.domain.salespost.vo.SalesPostVO;

public interface SalesPostService {
  int getSalesPostWrite(SalesPostVO svo);
  List<String> getFileIds(List<String> file_names);
  int getPostFileWrite(List<String> file_names);
  int getPostFileTableWrite(List<String> file_ids,Integer pwr_id);
  int getSelectLastInsert();
  List<SalesPostVO> getSalesPostList();
}
