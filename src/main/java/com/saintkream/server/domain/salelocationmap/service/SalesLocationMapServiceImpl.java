package com.saintkream.server.domain.salelocationmap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.salelocationmap.mapper.SalesLocationMapMapper;
import com.saintkream.server.domain.salelocationmap.vo.SalesLocationMapVO;

@Service
public class SalesLocationMapServiceImpl implements SalesLocationMapService{

  @Autowired
  private SalesLocationMapMapper salesLocationMapMapper;

  @Override
  public SalesLocationMapVO getSellerLocation(int pwr_id) {
    return salesLocationMapMapper.getSellerLocation(pwr_id);
  }

  @Override
  public List<SalesLocationMapVO> getNearbyLocations(double latitude, double longitude) {
    return salesLocationMapMapper.getNearbyLocations(latitude, longitude);
  }
  
}
