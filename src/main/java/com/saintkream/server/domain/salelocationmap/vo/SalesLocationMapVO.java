package com.saintkream.server.domain.salelocationmap.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesLocationMapVO {
    private String pwr_id,
                longitude,
                latitude;

}
