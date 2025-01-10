package com.saintkream.server.domain.salespost.service;

import java.util.List;
import java.util.Map;

import com.saintkream.server.domain.salespost.vo.SalesPostVO;

public interface SalesPostService {
  int getSalesPostWrite(SalesPostVO svo);
  int getSalesPostUpdate(SalesPostVO svo);
  List<String> getFileIds(List<String> file_names);
  int getPostFileWrite(List<String> file_names);
  int getPostFileUpdate(List<String> file_names);
  int getPostFileTableWrite(List<String> file_ids,Integer pwr_id);
  int getPostFileTableUpdate(List<String> file_ids,Integer pwr_id);
  int getSelectLastInsert();
  List<SalesPostVO> getSalesPostList();
  int upViewCount(int pwr_id);
  SalesPostVO getSalesPostOne(int pwr_id);
  int updateStatus(int pwr_id); // 판매완료 업데이트
  List<SalesPostVO> getSaleDetail(); // 판매 상세 페이지 리스트 나열
  List<SalesPostVO> getSellPostList(String member_id); // 판매내역 진행 중 리스트 나열
  List<String> getFileIdsByPwrId(String pwr_id);
  int deletePostFile(String file_id);
  int deleteFileTable(String file_id);
  int deletePost(String pwr_id);
}
