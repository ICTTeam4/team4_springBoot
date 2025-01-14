package com.saintkream.server.domain.salespost.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saintkream.server.domain.salespost.mapper.SalesPostMapper;
import com.saintkream.server.domain.salespost.vo.BookMarkVO;
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

  @Override
  public List<SalesPostVO> getSellPostList(String member_id) {
    return salesPostMapper.getSellPostList(member_id);
  }

  @Override
  public int getSalesPostUpdate(SalesPostVO svo) {
    return salesPostMapper.getSalesPostUpdate(svo);
  }

  @Override
  public int getPostFileUpdate(List<String> file_names) {
    return salesPostMapper.getPostFileUpdate(file_names);
  }

  @Override
  public int getPostFileTableUpdate(List<String> file_ids, Integer pwr_id) {
    return salesPostMapper.getPostFileTableUpdate(file_ids, pwr_id);
  }

  @Override
  public List<String> getFileIdsByPwrId(String pwr_id) {
    return salesPostMapper.getFileIdsByPwrId(pwr_id);
  }

  @Override
  public int deletePostFile(String file_id) {
    return salesPostMapper.deletePostFile(file_id);
  }

  @Override
  public int deleteFileTable(String file_id) {
    return salesPostMapper.deleteFileTable(file_id);
  }

  @Override
  public int deletePost(String pwr_id) {
    return salesPostMapper.deletePost(pwr_id);
  }

  // @Override
  // public BookMarkVO getBookMarkCheck(int pwr_id, int member_id) {
  //   return salesPostMapper.getBookMarkCheck(pwr_id, member_id);
  // }

  
  
}
