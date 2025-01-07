package com.saintkream.server.domain.salepage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.salepage.service.SalePageService;
import com.saintkream.server.domain.salepage.vo.SalePageVO;
import com.saintkream.server.domain.salepage.vo.SaleTabPageVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/salepage")
public class SalePageController {
  @Autowired
  private SalePageService salePageService;

  @GetMapping("/getpagedata")
  public DataVO getPageData(@RequestParam("id") int member_id) {
    log.info("@@@@@@@@@@@@@ member_id : {}", member_id);
    DataVO dataVO = new DataVO();
    try {
      SalePageVO SPVO = salePageService.getSalePageData(member_id);
      log.info("------------");
      log.info("SPVO : {}", SPVO);
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(SPVO);
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

  @GetMapping("/gettabdata")
  public DataVO getTabData(@RequestParam("id") int member_id) {
    log.info("@@@@@@@tabtabtab@@@@@@ member_id : {}", member_id);
    DataVO dataVO = new DataVO();
    try {
      List<SaleTabPageVO> list = salePageService.getSaleTabPageDataList(member_id);
      if (list == null || list.isEmpty()) {
        log.warn("No data found for member_id: {}", member_id);
        dataVO.setSuccess(false);
        dataVO.setMessage("데이터가 없습니다.");
        return dataVO;
      }
      log.info("Data fetched successfully.");
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(list);
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

}
