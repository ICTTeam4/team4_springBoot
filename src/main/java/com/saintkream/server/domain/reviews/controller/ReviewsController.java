package com.saintkream.server.domain.reviews.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.reviews.service.ReviewsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/HayoonReview")
public class ReviewsController {
    @Autowired
    private ReviewsService reviewsService;

    @RequestMapping("/submit")
    public ResponseEntity<?> submitReview(
            @RequestParam(name = "content") String content,
            @RequestParam(name = "seller_id") String seller_id,
            @RequestParam(name = "buyer_id") String buyer_id,
            @RequestParam(name = "pwr_id") int pwr_id,
            @RequestParam(name = "rate") int rate,
            @RequestParam(name = "member_id") int member_id, // member_id를 int로 변경
            @RequestParam(name = "images", required = false) MultipartFile[] images) {
        log.info("content: {}", content);
        log.info("seller_id: {}", seller_id);
        log.info("buyer_id: {}", buyer_id);
        log.info("pwr_id: {}", pwr_id);
        log.info("rate: {}", rate);
        log.info("member_id: {}", member_id);
        log.info("images: {}", (images != null ? images.length : 0));

        try {
            // 이미지가 없더라도 예외가 발생하지 않도록 서비스 호출
            reviewsService.saveReview(seller_id, buyer_id, pwr_id, content, rate, images, member_id);
            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Review submitted successfully"));
        } catch (Exception e) {
            log.error("Error submitting review", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Failed to submit review: " + e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getReviews(@RequestParam(name = "member_id", required = true) Integer member_id) {
        log.info("Received request with member_id: {}", member_id);

        try {
            // 내 후기만 가져옴
            List<Map<String, Object>> myReviews = reviewsService.getReviewsByMemberId(member_id);

            // 구매자와 판매자 리뷰를 가져옴
            List<Map<String, Object>> buyerOrSellerReviews = reviewsService.getReviewsByBuyerOrSeller(member_id);

            // 모든 데이터를 합침
            List<Map<String, Object>> allReviews = new ArrayList<>();
            allReviews.addAll(myReviews);
            allReviews.addAll(buyerOrSellerReviews);

            log.info("Fetched reviews: {}", allReviews);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", allReviews));
        } catch (Exception e) {
            log.error("Error fetching reviews", e);
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Failed to fetch reviews: " + e.getMessage()));
        }
    }

    /* 구매내역 pwr_id 검열 */
    @GetMapping("/getreviewpwr")
    public DataVO getReviewPwr(@RequestParam("member_id") String member_id) {
        DataVO dataVO = new DataVO();
        try{
        List<Integer> list = reviewsService.getReviewPwr(member_id);
        System.out.println( "list to String :  "+ list.toString());
        System.out.println("-----");
        dataVO.setSuccess(true);
        dataVO.setMessage("조회 성공");
        dataVO.setData(list);
        } catch (Exception e) {
        dataVO.setSuccess(false);
        dataVO.setMessage("조회 실패");
    }
    return dataVO;
    }

    /* 판매내역 pwr_id 검열 */
    @GetMapping("/getsellreviewpwr")
    public DataVO getSellReviewPwr(@RequestParam("member_id") String member_id) {
        DataVO dataVO = new DataVO();
        try{
        List<Integer> list = reviewsService.getSellReviewPwr(member_id);
        System.out.println( "list to String :  "+ list.toString());
        System.out.println("-----");
        dataVO.setSuccess(true);
        dataVO.setMessage("조회 성공");
        dataVO.setData(list);
        } catch (Exception e) {
        dataVO.setSuccess(false);
        dataVO.setMessage("조회 실패");
    }
    return dataVO;
    }
}