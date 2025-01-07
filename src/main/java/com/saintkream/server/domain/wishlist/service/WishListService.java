package com.saintkream.server.domain.wishlist.service;

import java.util.List;

import com.saintkream.server.domain.wishlist.vo.WishListVO;

public interface WishListService {
    // 찜 추가
    void addWishList(String member_id, String pwr_id);


    List<WishListVO> getWishList(String member_id);

    // 찜 지우기기
    void deleteWishListItem(String id);

    boolean isWishListed(String member_id, String pwr_id); // 찜 상태 확인 메서드

}
