package com.saintkream.server.domain.salelocationmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.salelocationmap.service.SalesLocationMapService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequestMapping("/api/saleslocationmap")
public class SalesLocationMapController {

  @Autowired
  private SalesLocationMapService salesLocationMapService;

  @GetMapping("path")
  public DataVO getSellerLocation(@RequestParam("id") int pwr_id) {
    DataVO dataVO = new DataVO();
      return null;
  }
  
  
}
