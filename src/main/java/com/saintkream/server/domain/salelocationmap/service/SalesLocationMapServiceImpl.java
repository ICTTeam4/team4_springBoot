package com.saintkream.server.domain.salelocationmap.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.salelocationmap.mapper.SalesLocationMapMapper;
import com.saintkream.server.domain.salelocationmap.vo.SalesLocationMapVO;

@Service
public class SalesLocationMapServiceImpl implements SalesLocationMapService {

  @Autowired
  private SalesLocationMapMapper salesLocationMapMapper;

  @Override
  public SalesLocationMapVO getSellerLocation(int pwr_id) {
    return salesLocationMapMapper.getSellerLocation(pwr_id);
  }

  // @Override
  // public List<SalesLocationMapVO> getDdrLocation() {
  // return salesLocationMapMapper.getDdrLocation();
  // }

  // @Override
  // public List<SalesLocationMapVO> getDdrLocation() {
  //   List<SalesLocationMapVO> result = salesLocationMapMapper.getDdrLocation();
  //   result.forEach(location -> {
  //     if (location.getDdr_id() == null)
  //       location.setDdr_id("");
  //     if (location.getDdr_addr_detail() == null)
  //       location.setDdr_addr_detail("");
  //     if (location.getDdr_lat() == null)
  //       location.setDdr_lat(0.0);
  //     if (location.getDdr_lng() == null)
  //       location.setDdr_lng(0.0);
  //   });
  //   return result;
  // }

  // @Override
  // public List<SalesLocationMapVO> getLaundryLocation() {
  // return salesLocationMapMapper.getLaundryLocation();
  // }

  // @Override
  // public List<SalesLocationMapVO> getLaundryLocation() {
  //   List<SalesLocationMapVO> result = salesLocationMapMapper.getLaundryLocation();
  //   result.forEach(location -> {
  //     if (location.getLaundry_id() == null)
  //       location.setLaundry_id("");
  //     if (location.getLaundry_name() == null)
  //       location.setLaundry_name("");
  //     if (location.getLaundry_lat() == null)
  //       location.setLaundry_lat(0.0);
  //     if (location.getLaundry_lng() == null)
  //       location.setLaundry_lng(0.0);
  //   });
  //   return result;
  // }

  @Override
public List<SalesLocationMapVO> getDdrLocation() {
    List<SalesLocationMapVO> result = salesLocationMapMapper.getDdrLocation();
    return result == null ? Collections.emptyList() : result;
}

@Override
public List<SalesLocationMapVO> getLaundryLocation() {
    List<SalesLocationMapVO> result = salesLocationMapMapper.getLaundryLocation();
    return result == null ? Collections.emptyList() : result;
}
}
