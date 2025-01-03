package com.saintkream.server.domain.salelocationmap.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesLocationMapVO {
  // post테이블 정보
  private String pwr_id,
      longitude,
      latitude;

  // 따릉이 테이블 정보
  // 세탁소 테이블 정보
  private int ddr_lat,
      ddr_lng,
      laundry_lat,
      laundry_lng;
}
