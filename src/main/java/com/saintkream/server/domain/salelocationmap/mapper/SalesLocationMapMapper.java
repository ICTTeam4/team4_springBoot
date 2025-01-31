package com.saintkream.server.domain.salelocationmap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.salelocationmap.vo.SalesLocationMapVO;


@Mapper
public interface SalesLocationMapMapper {
  
  SalesLocationMapVO getSellerLocation(int pwr_id);
  List<SalesLocationMapVO> getDdrLocation();
  List<SalesLocationMapVO> getLaundryLocation();
}
