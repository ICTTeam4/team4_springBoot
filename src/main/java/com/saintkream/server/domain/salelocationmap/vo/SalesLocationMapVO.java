package com.saintkream.server.domain.salelocationmap.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesLocationMapVO {

  // post 테이블 정보
  private String pwr_id;         // 판매자 고유 ID
  private String longitude;      // 경도 (String으로 처리)
  private String latitude;       // 위도 (String으로 처리)

  // 따릉이 테이블 정보
  private String ddr_id;         // 따릉이 대여소 고유 ID
  private String ddr_addr_detail;// 따릉이 대여소 상세 주소
  private Double ddr_lat;        // 따릉이 대여소 위도 (DB의 double)
  private Double ddr_lng;        // 따릉이 대여소 경도 (DB의 double)

  // 세탁소 테이블 정보
  private String laundry_id;     // 세탁소 고유 ID
  private String laundry_name;   // 세탁소 이름
  private Double laundry_lat;    // 세탁소 위도 (DB의 double)
  private Double laundry_lng;    // 세탁소 경도 (DB의 double)
}