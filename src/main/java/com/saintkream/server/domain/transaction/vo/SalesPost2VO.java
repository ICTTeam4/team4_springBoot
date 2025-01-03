package com.saintkream.server.domain.transaction.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesPost2VO {
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
  private List<File2VO> fileList;
  private String name;
}
