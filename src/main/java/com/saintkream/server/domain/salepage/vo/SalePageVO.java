package com.saintkream.server.domain.salepage.vo;

import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.Data;

@Data
public class SalePageVO {
  int member_id = 0;
  String nickname = "닉네임 없음";
  String content = "내용 없음";
  double rate = 0.0;
  String file_name = "파일 없음";
  String profile_image = ""; // 기본 프로필 이미지
  private List<MultipartFile> files = Collections.emptyList();
  private List<FileVO> fileList = Collections.emptyList();
}
