package com.saintkream.server.domain.salespost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequestMapping("/api/salespost")
public class SalesPostController {
  @GetMapping("/salesdetail")
  public String getPostDetail() {

      return "응답응답";
  }
  
}
