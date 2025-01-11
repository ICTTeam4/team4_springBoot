package com.saintkream.server.domain.salepage.service;

import java.util.List;

import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleReviewPageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

public interface SalePageService {
  SalePageVO getSalePageData(int member_id);
  List<SaleTabPageVO> getSaleTabPageDataList(int member_id);
  List<SaleReviewPageVO> getSaleReviewDataList(String member_id);
  List<SalesPostVO> getSellDoneData(String member_id);
}
