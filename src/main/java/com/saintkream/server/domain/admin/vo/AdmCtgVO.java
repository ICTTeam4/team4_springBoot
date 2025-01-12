package com.saintkream.server.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmCtgVO {

  private String category_id, category_name, parent_category_id;
  
}
