package com.saintkream.server.domain.searchitems.vo;

import lombok.Data;

@Data
public class SearchItemsVO {
  private int pwr_id;              // PK (int)
  private int member_id;           // 회원 ID (int)
  private String selling_area_id;  // 판매 지역 ID (varchar -> String)
  private String title;            // 제목 (varchar -> String)
  private String status;           // 상태 (varchar -> String)
  private int sell_price;          // 판매 가격 (int)
  private int view_count;          // 조회 수 (int)
  private String description;      // 설명 (varchar -> String)
  private String created_at;       // 생성일 (timestamp -> String)
  private boolean is_direct;       // 직거래 여부 (tinyint -> boolean)
  private boolean is_delivery;     // 배송 여부 (tinyint -> boolean)
  private String sup_category;     // 상위 카테고리 (varchar -> String)
  private String sub_category;     // 하위 카테고리 (varchar -> String)
}