package com.saintkream.server.domain.salepage.vo;

import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleTabPageVO {
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
  private List<FileVO> fileList;
  private String name;
}
