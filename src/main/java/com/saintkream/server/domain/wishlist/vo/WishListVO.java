package com.saintkream.server.domain.wishlist.vo;

import java.util.List;

import com.saintkream.server.domain.salespost.vo.FileVO;

import lombok.Data;

@Data
public class WishListVO{
    private String id;            // 찜한 항목 ID
    private String member_id;      // 사용자 ID
    private String pwr_id;     // 상품 ID
    private String created_at; // 찜한 시간
    private List<FileVO> fileList; // 파일리스트 추가가
    private String sell_price;
    private String title;
    private boolean status;
    // 
}