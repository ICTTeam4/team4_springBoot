package com.saintkream.server.domain.salepage.service;

import java.util.List;

import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;

public interface SalePageService {
  SalePageVO getSalePageData(int member_id);
  List<SaleTabPageVO> getSaleTabPageDataList(int member_id);
}
