package com.saintkream.server.domain.salepage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.salepage.mapper.SalePageMapper;
import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;

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
  
}
