package com.saintkream.server.domain.salespost.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.salespost.service.SalesPostService;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/api/salespost")
public class SalesPostController {
  @Autowired
  private SalesPostService salesPostService;

  @PostMapping("/salesinsert")
  public DataVO getPostDetail(@ModelAttribute("data") SalesPostVO svo) {
    DataVO dataVO = new DataVO();
    System.out.println("svo.tostring()"+svo.toString());
    try {
      
      if (svo.getIs_delivery().equals("false")) {
        svo.setIs_delivery("0");
      } else {
        svo.setIs_delivery("1");
      }
      if (svo.getIs_direct().equals("false")) {
        svo.setIs_direct("0");
      } else {
        svo.setIs_direct("1");
      }
      List<String> file_names = new ArrayList<>();
      salesPostService.getSalesPostWrite(svo);
      int pwr_id = salesPostService.getSelectLastInsert();
      if (svo.getFiles() != null) {
        List<MultipartFile> files = svo.getFiles();
        for (MultipartFile file : files) {
          if (file.isEmpty())
            continue;
          UUID uuid = UUID.randomUUID();
          String file_name = uuid.toString() + "_" + file.getOriginalFilename();
          System.out.println(file_name);
          String staticPath = new File("src/main/resources/static/images").getAbsolutePath();
          File uploadDir = new File(staticPath);
          file.transferTo(new File(uploadDir, file_name));
          file_names.add(file_name);
        }
        salesPostService.getPostFileWrite(file_names);
        List<String> file_ids = salesPostService.getFileIds(file_names);
        int result = salesPostService.getPostFileTableWrite(file_ids,pwr_id);
        if (result == 0) {
          dataVO.setSuccess(false);
          dataVO.setMessage("게스트북 수정 실패");
          return dataVO;
      }
      dataVO.setSuccess(true);
      dataVO.setMessage("게스트북 수정 성공");
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return dataVO;
  }

  @GetMapping("/itemlist")
  public DataVO getitemList() {
    DataVO dataVO = new DataVO();
    try {
      List<SalesPostVO> list = salesPostService.getSalesPostList();
      System.out.println( "list to String :  "+ list.toString());
      System.out.println("-----");
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(list);
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

  @GetMapping("/itemone")
  public DataVO getitemOne(@RequestParam("id") int pwr_id) {
    DataVO dataVO = new DataVO();
    try {
      SalesPostVO SPVO = salesPostService.getSalesPostOne(pwr_id);
      log.info("------------");
      log.info("SPVO:", SPVO.getFile_name());
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(SPVO);

    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

  @PostMapping("/upviewcount")
  public Map<String, String> upViewCount(@RequestBody Map<String, Integer> request) {
    int pwr_id = request.get("id");
    int result = salesPostService.upViewCount(pwr_id);

    Map<String, String> response = new HashMap<>();
    if (result > 0) {
        response.put("message", "조회수 증가 완료");
    } else {
        response.put("message", "조회수 증가 실패");
    }
    return response;
  }
  
  @PostMapping("/updatestatus")
  public Map<String, String> updataStatus(@RequestBody Map<String, Integer> request) {
    int pwr_id = request.get("pwr_id");
    int result = salesPostService.updateStatus(pwr_id);

    Map<String, String> response = new HashMap<>();
    if (result > 0) {
        response.put("message", "판매 완료");
    } else {
        response.put("message", "판매완료 실패");
    }
    return response;
  }
  

}
