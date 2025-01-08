package com.saintkream.server.domain.recent_view.vo;

import java.util.List;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.Data;

@Data
public class RecentViewVO {
    private String recentId;
    private String member_id;
    private String pwr_id;
    private String created_at;
    private String sell_price;
    private String title;
    
    private List<FileVO> fileList; // 파일리스트 추가가
}
