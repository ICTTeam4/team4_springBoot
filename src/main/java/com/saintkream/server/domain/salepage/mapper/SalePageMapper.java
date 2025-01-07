package com.saintkream.server.domain.salepage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;

@Mapper
public interface SalePageMapper {
  SalePageVO getSalePageData(int member_id);
  List<SaleTabPageVO> getSaleTabPageDataList(int member_id);
}
