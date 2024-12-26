package com.saintkream.server.domain.salespost.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.auth.vo.DataVO;
import com.saintkream.server.domain.salespost.service.SalesPostService;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/api/salespost")
public class SalesPostController {
  @Autowired
  private SalesPostService salesPostService;

  @PostMapping("/salesinsert")
  public String getPostDetail(@ModelAttribute("data") SalesPostVO svo) {
    DataVO dataVO = new DataVO();
    try {
      
      System.out.println("-----------------------------------------");
      if (svo.getIs_delivery().equals("false")) {
        svo.setIs_delivery("0");
      } else {
        svo.setIs_delivery("1");
      }
      if (svo.getIs_direct().equals("true")) {
        svo.setIs_direct("1");
      } else {
        svo.setIs_direct("0");
      }
      salesPostService.getSalesPostWrite(svo);
      if (svo.getFiles() != null) {
        List<MultipartFile> files = svo.getFiles();
        for (MultipartFile file : files) {
          if (file.isEmpty())
            continue;
          UUID uuid = UUID.randomUUID();
          String file_name = uuid.toString() + "_" + file.getOriginalFilename();
          System.out.println(file_name);
          String path = "/upload";
          // File uploadDir = new File(path);
          // if (!uploadDir.exists()) {
          //   uploadDir.mkdirs();
          // }
          String staticPath = new File("src/main/resources/static").getAbsolutePath();
          File uploadDir = new File(staticPath);
          file.transferTo(new File(uploadDir, file_name));
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return "응답응답";
  }

}
