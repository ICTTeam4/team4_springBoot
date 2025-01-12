package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmCycleVO {
  private String ddr_id, ddr_addr, ddr_addr_detail, ddr_lat, ddr_lng;
}


