package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmTransVO {
  private String pwr_id, member_id, selling_area_id, title, status, sell_price, view_count, description, created_at, is_direct, is_delivery, sup_category, sub_category, longitude, latitude, trans_id, trans_price, trans_date, trans_method, buyer, seller, fixed_date;
}
