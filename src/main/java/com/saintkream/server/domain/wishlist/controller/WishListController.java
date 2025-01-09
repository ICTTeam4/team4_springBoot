package com.saintkream.server.domain.wishlist.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saintkream.server.domain.wishlist.service.WishListService;
import com.saintkream.server.domain.wishlist.vo.WishListVO;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/wishlist")
public class WishListController {
    
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }



    @PostMapping("/add")
public ResponseEntity<String> addWishList(@RequestBody WishListVO wishListVO) {
    try {
        log.info("찜 추가 요청 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
        wishListService.addWishList(wishListVO.getMember_id(), wishListVO.getPwr_id());
        return ResponseEntity.ok("찜하기 완료~!");
    } catch (DuplicateKeyException e) {
        log.warn("중복된 찜 추가 요청 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 찜한 상품입니다.");
    }
}

    
    @GetMapping("/list")
    public ResponseEntity<List<WishListVO>> getWishList(@RequestParam("memberId") String memberId) {
        log.info("찜 목록 조회 요청 - 회원 ID: {}", memberId);
         List<WishListVO> wishList = wishListService.getWishList(memberId);
         log.info("찜 목록 조회 성공 - 회원 ID: {}, 조회된 항목 수: {}", memberId, wishList.size());
         log.info("wishList" + wishList.toString());
         return ResponseEntity.ok(wishList);
        // return ResponseEntity.ok(null);
    }

    @DeleteMapping("/delete")
public ResponseEntity<String> deleteWishListItem(@RequestBody WishListVO wishListVO) {
    log.info("찜 삭제 요청 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
    wishListService.deleteWishListItem(wishListVO.getMember_id(), wishListVO.getPwr_id());
    log.info("찜 삭제 성공 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
    return ResponseEntity.ok("찜한 상품 삭제 완료~!");
}


    // 찜 상태 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkWishList(
            @RequestParam("memberId") String memberId,
            @RequestParam("pwr_id") String pwr_id) {
        log.info("찜 상태 확인 요청 - 회원 ID: {}, 상품 ID: {}", memberId, pwr_id);
        boolean isWishListed = wishListService.isWishListed(memberId, pwr_id);
        log.info("찜 상태 확인 결과 - 회원 ID: {}, 상품 ID: {}, 찜 상태: {}", memberId, pwr_id, isWishListed ? "찜함" : "찜하지 않음");
        return ResponseEntity.ok(isWishListed);
    }
}
