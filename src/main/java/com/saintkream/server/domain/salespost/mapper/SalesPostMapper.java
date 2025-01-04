package com.saintkream.server.domain.salespost.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.salespost.vo.SalesPostVO;

@Mapper
public interface SalesPostMapper {
  int getSalesPostWrite(SalesPostVO svo);
  List<String> getFileIds(@Param("file_names") List<String> file_names);
  int getPostFileWrite(@Param("file_names") List<String> file_names);
  int getPostFileTableWrite(@Param("file_ids") List<String> file_ids, @Param("pwr_id") Integer pwr_id);
  int getSelectlastInsert();
  List<SalesPostVO> getSalesPostList();
  int upViewCount(int pwr_id);
  SalesPostVO getSalesPostOne(int pwr_id);
  int updateStatus(int pwr_id);
}
