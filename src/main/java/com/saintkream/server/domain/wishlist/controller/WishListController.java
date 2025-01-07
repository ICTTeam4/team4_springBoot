package com.saintkream.server.domain.wishlist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.wishlist.service.WishListService;
import com.saintkream.server.domain.wishlist.vo.WishListVO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/wishlist")
public class WishListController {
    
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("/add")
    public String addWishList(@RequestParam("memberId") int memberId, @RequestParam("pwr_id") int pwr_id) {
        log.info("찜 추가 요청 - 회원 ID: {}, 상품 ID: {}", memberId, pwr_id);
        wishListService.addWishList(memberId, pwr_id);
        log.info("찜 추가 성공 - 회원 ID: {}, 상품 ID: {}", memberId, pwr_id);
        return "찜하기 완료~!";
    }

    @GetMapping("/list")
    public List<WishListVO> getWishList(@RequestParam("memberId") int memberId) {
        log.info("찜 목록 조회 요청 - 회원 ID: {}", memberId);
        List<WishListVO> wishList = wishListService.getWishList(memberId);
        log.info("찜 목록 조회 성공 - 회원 ID: {}, 조회된 항목 수: {}", memberId, wishList.size());
        return wishList;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteWishListItem(@PathVariable int id) {
        log.info("찜 삭제 요청 - 찜 ID: {}", id);
        wishListService.deleteWishListItem(id);
        log.info("찜 삭제 성공 - 찜 ID: {}", id);
        return "찜한 상품 삭제 완료~!";
    }

    @GetMapping("/check")
    public boolean checkWishList(
            @RequestParam("memberId") int memberId,
            @RequestParam("pwr_id") int pwr_id) {
        log.info("찜 상태 확인 요청 - 회원 ID: {}, 상품 ID: {}", memberId, pwr_id);
        boolean isWishListed = wishListService.isWishListed(memberId, pwr_id);
        log.info("찜 상태 확인 결과 - 회원 ID: {}, 상품 ID: {}, 찜 상태: {}", memberId, pwr_id, isWishListed ? "찜함" : "찜하지 않음");
        return isWishListed;
    }
}