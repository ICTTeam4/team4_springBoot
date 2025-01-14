package com.saintkream.server.domain.salepage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleReviewPageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

@Mapper
public interface SalePageMapper {
  SalePageVO getSalePageData(int member_id);
  List<SaleTabPageVO> getSaleTabPageDataList(int member_id);
  List<SaleReviewPageVO> getSaleReviewDataList(String member_id);
  List<SalesPostVO> getSellDoneData(String member_id);
}
