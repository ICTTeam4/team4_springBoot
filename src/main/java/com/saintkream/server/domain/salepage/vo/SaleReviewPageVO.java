package com.saintkream.server.domain.salepage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleReviewPageVO {
  private String review_id = "rvid";
  private String seller_id = "";
  private String buyer_id = "";
  private String content = "";
  private String created_at = "";
  private String nickname = "";
  private String profile_image = "";
  private String is_deleted = "";
  private int rate = 0;
}
                 
