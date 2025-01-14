package com.saintkream.server.domain.salespost.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.salespost.vo.BookMarkVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

@Mapper
public interface SalesPostMapper {
  int getSalesPostWrite(SalesPostVO svo);
  int getSalesPostUpdate(SalesPostVO svo);
  List<String> getFileIds(@Param("file_names") List<String> file_names);
  int getPostFileWrite(@Param("file_names") List<String> file_names);
  int getPostFileUpdate(@Param("file_names") List<String> file_names);
  int getPostFileTableWrite(@Param("file_ids") List<String> file_ids, @Param("pwr_id") Integer pwr_id);
  int getPostFileTableUpdate(@Param("file_ids") List<String> file_ids, @Param("pwr_id") Integer pwr_id);
  int getSelectlastInsert();
  List<SalesPostVO> getSalesPostList();
  int upViewCount(int pwr_id);
  SalesPostVO getSalesPostOne(int pwr_id);
  // BookMarkVO getBookMarkCheck(int pwr_id, int member_id);
  int updateStatus(int pwr_id);
  List<SalesPostVO> getSaleDetail();
  List<SalesPostVO> getSellPostList(String member_id);
  List<String> getFileIdsByPwrId(String pwr_id);
  int deletePostFile(String file_id);
  int deleteFileTable(String file_id);
  int deletePost(String pwr_id);
}
