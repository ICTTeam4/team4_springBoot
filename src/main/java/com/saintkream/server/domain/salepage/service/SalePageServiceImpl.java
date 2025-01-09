package com.saintkream.server.domain.salepage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.salepage.mapper.SalePageMapper;
import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleReviewPageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalePageServiceImpl implements SalePageService {

  @Autowired
  private SalePageMapper salePageMapper;

  @Override
  public SalePageVO getSalePageData(int member_id) {
    return salePageMapper.getSalePageData(member_id);
  }

  @Override
  public List<SaleTabPageVO> getSaleTabPageDataList(int member_id) {
    return salePageMapper.getSaleTabPageDataList(member_id);
  }

  @Override
  public List<SaleReviewPageVO> getSaleReviewDataList(String member_id) {
    List<SaleReviewPageVO> result = salePageMapper.getSaleReviewDataList(member_id);
    log.info("쿼리 결과: {}", result);
    return salePageMapper.getSaleReviewDataList(member_id);

  }
  
}
