package com.saintkream.server.domain.wishlist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.wishlist.vo.WishListVO;

@Mapper
public interface WishListMapper {

    // 찜 추가
    void addWishList(WishListVO wishListVO);

    // 찜 조회
    List<WishListVO> getWishListByMemberId(String member_id);

    // 찜 지우기기
    void deleteWishListItem(@Param("member_id") String member_id, @Param("pwr_id") String pwr_id);

        // 찜 상태 확인 
     int checkWishList(@Param("member_id") String member_id, @Param("pwr_id") String pwr_id);
}

