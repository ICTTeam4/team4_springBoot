package com.saintkream.server.domain.salelocationmap.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.salelocationmap.service.SalesLocationMapService;
import com.saintkream.server.domain.salelocationmap.vo.SalesLocationMapVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/api/saleslocationmap")
public class SalesLocationMapController {



  @GetMapping("/locationinfo")
  public DataVO getLocationInfo(@RequestParam("id") int pwr_id) {
    DataVO dataVO = new DataVO();
    try {
      SalesLocationMapVO SLMVO = salesLocationMapService.getSellerLocation(pwr_id);
      List<SalesLocationMapVO> nearbyLocations = salesLocationMapService.getNearbyLocations(
                Double.parseDouble(SLMVO.getLatitude()),
                Double.parseDouble(SLMVO.getLongitude())
      );
      log.info("------------");
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(Map.of(
        "SLMVO", SLMVO,
        "nearbyLocations", nearbyLocations
      ));
      log.info("SLMVO : ", SLMVO);
      log.info("nearbyLocations : ", nearbyLocations);

    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

}
