package com.saintkream.server.domain.salespost.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesPostVO {
  private String pwr_id,member_id,selling_area_id,title,status,sell_price,view_count,description,created_at,file_name,category_id,is_direct,is_delivery;
  private List<MultipartFile> files;
}
