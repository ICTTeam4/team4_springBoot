package com.saintkream.server.domain.wishlist.controller;

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

    // @PostMapping("/add")
    // public String addWishList(@RequestParam("memberId") int memberId, @RequestParam("pwr_id") int pwr_id) {
    //     log.info("찜 추가 요청 - 회원 ID: {}, 상품 ID: {}", memberId, pwr_id);
    //     wishListService.addWishList(memberId, pwr_id);
    //     log.info("찜 추가 성공 - 회원 ID: {}, 상품 ID: {}", memberId, pwr_id);
    //     return "찜하기 완료~!";
    // }

    // 찜 추가 (JSON 요청)
    @PostMapping("/add")
    public ResponseEntity<String> addWishList(@RequestBody WishListVO wishListVO) {
        log.info("찜 추가 요청 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
        wishListService.addWishList(wishListVO.getMember_id(), wishListVO.getPwr_id());

         // 추가 확인
    // if (wishListVO.getPwr_id() == 0) {
    //     log.error("pwr_id가 0으로 매핑되었습니다. JSON 매핑 문제일 수 있습니다.");
    // }

           // 만약 pwr_id가 0이거나 null이라면 여기서 확인 가능
            wishListService.addWishList(wishListVO.getMember_id(), wishListVO.getPwr_id());

        log.info("찜 추가 성공 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
        return ResponseEntity.ok("찜하기 완료~!");
    }

    // 찜 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<WishListVO>> getWishList(@RequestParam("memberId") String memberId) {
        log.info("찜 목록 조회 요청 - 회원 ID: {}", memberId);
         List<WishListVO> wishList = wishListService.getWishList(memberId);
         log.info("찜 목록 조회 성공 - 회원 ID: {}, 조회된 항목 수: {}", memberId, wishList.size());
         log.info("wishList" + wishList.toString());
         return ResponseEntity.ok(wishList);
        // return ResponseEntity.ok(null);
    }

    // 찜 삭제 (JSON 요청)
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWishListItem(@RequestBody WishListVO wishListVO) {
        log.info("찜 삭제 요청 - 회원 ID: {}, 상품 ID: {}", wishListVO.getMember_id(), wishListVO.getPwr_id());
        wishListService.deleteWishListItem(wishListVO.getId());
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
