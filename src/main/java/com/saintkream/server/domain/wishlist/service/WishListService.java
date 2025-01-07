package com.saintkream.server.domain.wishlist.service;

import java.util.List;

import com.saintkream.server.domain.wishlist.vo.WishListVO;

public interface WishListService {
    // 찜 추가
    void addWishList(int member_id, int pwr_id);


    List<WishListVO> getWishList(int member_id);

    // 찜 지우기기
    void deleteWishListItem(int id);

    boolean isWishListed(int member_id, int pwr_id); // 찜 상태 확인 메서드

}
