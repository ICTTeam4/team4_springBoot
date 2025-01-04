package com.saintkream.server.domain.salespost.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saintkream.server.domain.salespost.mapper.SalesPostMapper;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

@Service
public class SalesPostServiceImpl implements SalesPostService{
  @Autowired
  private SalesPostMapper salesPostMapper;

  @Override
  public int getSalesPostWrite(SalesPostVO svo) {
    System.out.println("entered Sales Post write service impl");
    System.out.println(svo.toString());
    svo.setPwr_id("1");
    return salesPostMapper.getSalesPostWrite(svo);
  }

  @Override
  public List<String> getFileIds(List<String> file_names) {
    return salesPostMapper.getFileIds(file_names);
  }

  @Override
  public int getPostFileWrite(List<String> file_names) {
    return salesPostMapper.getPostFileWrite(file_names);
  }

  @Override
  public int getPostFileTableWrite(List<String> file_ids, Integer pwr_id) {
    return salesPostMapper.getPostFileTableWrite(file_ids, pwr_id);
  }

  @Override
  public int getSelectLastInsert() {
    return salesPostMapper.getSelectlastInsert();
  }

  @Override
  public List<SalesPostVO> getSalesPostList() {
    return salesPostMapper.getSalesPostList();
  }

  @Override
  public int upViewCount(int pwr_id) {
    return salesPostMapper.upViewCount(pwr_id);
  }

  @Override
  public SalesPostVO getSalesPostOne(int pwr_id) {
    return salesPostMapper.getSalesPostOne(pwr_id);
  }

  @Override
  public int updateStatus(int pwr_id) {
    return salesPostMapper.updateStatus(pwr_id);
  }

  @Override
  public List<SalesPostVO> getSaleDetail() {
    return salesPostMapper.getSaleDetail();
  }

  
  
}
