package com.saintkream.server.domain.recent_view.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.recent_view.service.RecentViewService;
import com.saintkream.server.domain.recent_view.vo.RecentViewVO;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/recent-view")
public class RecentViewController {
    
    private static final Logger logger = LoggerFactory.getLogger(RecentViewController.class);

    @Autowired
    private RecentViewService recentViewService;

    // @GetMapping("/{memberId}")
    // public ResponseEntity<?> getRecentlyViewedByMemberId (@PathVariable String memberId) {
    //     logger.info("최근 본 아이디!!!!!!!!!!! memberId: {}", memberId);
    //     List<RecentViewVO> recentViews = recentViewService.getRecentlyViewedByMemberId(memberId);
    //     logger.info("최근 본 아이템!!!!!!!!!!!!!! items: {}", recentViews);
    //     return ResponseEntity.ok(recentViews);
    // }

    @GetMapping("/list")
public ResponseEntity<List<RecentViewVO>> getRecentlyViewedByMemberId(@RequestParam("member_id") String member_id) {
    logger.info("최근 본 아이디!!!!!!!!!!! memberId: {}", member_id);
    List<RecentViewVO> recentViews = recentViewService.getRecentlyViewedByMemberId(member_id);

    // Null 필터링
    recentViews = recentViews.stream()
                             .filter(item -> item != null && item.getPwr_id() != null)
                             .collect(Collectors.toList());

    logger.info("최근 본 아이템!!!!!!!!!!!!!! items: {}", recentViews);
    return ResponseEntity.ok(recentViews);
}
//     @GetMapping("/{memberId}")
// public ResponseEntity<?> getRecentlyViewedByMemberId(@PathVariable String memberId) {
//     logger.info("최근 본 아이디!!!!!!!!!!! memberId: {}", memberId);
//     List<RecentViewVO> recentViews = recentViewService.getRecentlyViewedByMemberId(memberId);

//     // Null 필터링
//     recentViews = recentViews.stream()
//                              .filter(item -> item != null && item.getPwr_id() != null)
//                              .collect(Collectors.toList());

//     logger.info("최근 본 아이템!!!!!!!!!!!!!! items: {}", recentViews);
//     return ResponseEntity.ok(recentViews);
// }

//    @GetMapping("/list")
//     public ResponseEntity<List<RecentViewVO>> getWishList(@RequestParam("memberId") String memberId) {
//         log.info("최근 본본 목록 조회 요청 - 회원 ID: {}", memberId);
//          List<RecentViewVO> recentView = recentViewService.getRecentView(memberId);
//          log.info("최근 본본 목록 조회 성공 - 회원 ID: {}, 조회된 항목 수: {}", memberId, recentView.size());
//          log.info("wishList" + recentView.toString());
//          return ResponseEntity.ok(recentView);
//         // return ResponseEntity.ok(null);
//     } 
   
@PostMapping
public ResponseEntity<?> insertRecentlyViewed(@RequestBody RecentViewVO rvo) {
    logger.info("최근 본 상품 저장 요청 수신: {}", rvo);
    logger.info("rvo get pwr_id", rvo.getPwr_id());
    logger.info("rvo get member_id", rvo.getMember_id());
    try {
        recentViewService.insertRecentlyViewed(rvo);
        logger.info("최근 본 상품 저장 성공: {}", rvo);
        return ResponseEntity.ok("Item successfully inserted");
    } catch (Exception e) {
        logger.error("최근 본 상품 저장 중 오류 발생: ", e);
        return ResponseEntity.status(500).body("Error saving item");
    }
}






// @PostMapping
// public ResponseEntity<String> insertRecentlyViewed(@RequestBody RecentViewVO rvo) {
//     logger.info("최근 본 상품 저장 요청 수신: {}", rvo);

//     // 검증: member_id와 pwr_id가 null 또는 비어 있는지 확인
//     if (rvo.getMember_id() == null || rvo.getMember_id().isEmpty()) {
//         logger.error("Error: member_id가 누락되었습니다.");
//         return ResponseEntity.badRequest().body("Error: member_id is required.");
//     }
//     if (rvo.getPwr_id() == null || rvo.getPwr_id().isEmpty()) {
//         logger.error("Error: pwr_id가 누락되었습니다.");
//         return ResponseEntity.badRequest().body("Error: pwr_id is required.");
//     }

//     try {
//         recentViewService.insertRecentlyViewed(rvo.getMember_id(), rvo.getPwr_id());
//         logger.info("최근 본 상품 저장 성공: {}", rvo);
//         return ResponseEntity.ok("Item successfully inserted");
//     } catch (Exception e) {
//         logger.error("최근 본 상품 저장 중 오류 발생: ", e);
//         return ResponseEntity.status(500).body("Error saving item");
//     }
// }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRecentlyViewed(@RequestBody RecentViewVO recentViewVO) {
        logger.info("최근 본 상품 아이디 지우기!!! recentId: {}", recentViewVO);
        recentViewService.deleteRecentlyViewed(recentViewVO.getMember_id(), recentViewVO.getPwr_id());
        logger.info("최근 본 상품 아이디 지우기 성공!! recentId: {}", recentViewVO);
        return ResponseEntity.ok("최근 본 상품 삭제 성공!");
    }
}
