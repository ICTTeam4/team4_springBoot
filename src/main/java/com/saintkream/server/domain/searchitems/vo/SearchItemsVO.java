package com.saintkream.server.domain.searchitems.vo;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class SearchItemsVO {
  private String pwr_id,
      member_id,
      selling_area_id,
      title,
      status,
      sell_price,
      view_count,
      description,
      created_at,
      file_name,
      sup_category,
      sub_category,
      is_direct,
      is_delivery,
      longitude,
      latitude;
  private List<MultipartFile> files;
  private List<SearchItemsFileVO> fileList;
  private String name;
}
  