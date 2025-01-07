package com.saintkream.server.domain.salepage.vo;

import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.Data;

@Data
public class SaleTabPageVO {
  int member_id = 0;
  private int pwr_id;          // 게시물 ID
    private String file_name;    // 게시물 이미지 파일 이름
    private String title;        // 게시물 제목
    private int sell_price;      // 게시물 가격
    private String created_at;   // 등록 시간
    private String status;       // 게시물 상태 (판매중/종료)
    private String trans_date;   // 거래 완료 시간 (판매 완료 시)
  private List<MultipartFile> files = Collections.emptyList();
  private List<FileVO> fileList = Collections.emptyList();
}
