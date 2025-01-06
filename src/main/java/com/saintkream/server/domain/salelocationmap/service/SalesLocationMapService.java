package com.saintkream.server.domain.salelocationmap.service;

import java.util.List;

import com.saintkream.server.domain.salelocationmap.vo.SalesLocationMapVO;

public interface SalesLocationMapService {
  SalesLocationMapVO getSellerLocation(int pwr_id);
  List<SalesLocationMapVO> getDdrLocation();
  List<SalesLocationMapVO> getLaundryLocation();
}
