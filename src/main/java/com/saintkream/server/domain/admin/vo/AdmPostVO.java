package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmPostVO {
   private String pwr_id, member_id, selling_area_id, title, status, sell_price, view_count, description, created_at, is_direct, is_delivery, sup_category, sub_category , nickname	, file_name;
}
