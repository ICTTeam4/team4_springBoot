package com.saintkream.server.domain.wishlist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saintkream.server.domain.wishlist.mapper.WishListMapper;
import com.saintkream.server.domain.wishlist.vo.WishListVO;


@Transactional
@Service
public class WishListServiceImpl implements WishListService{

    private final WishListMapper mapper;

    public WishListServiceImpl(WishListMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean isWishListed(String member_id, String pwr_id) {
        return mapper.checkWishList(member_id, pwr_id) > 0;
    }


 
    @Override
    public void deleteWishListItem(String id) {
        mapper.deleteWishListItem(id);
    }

    @Override
    public void addWishList(String member_id, String pwr_id) {
        WishListVO wishlist = new WishListVO();
        wishlist.setMember_id(member_id);
        wishlist.setPwr_id(pwr_id);
        mapper.addWishList(wishlist);
    }



    @Override
    public List<WishListVO> getWishList(String member_id) {
        return mapper.getWishListByMemberId(member_id);
    }




  


}
