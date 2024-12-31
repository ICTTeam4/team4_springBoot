package com.saintkream.server.domain.reviews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.reviews.service.ReviewsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/HayoonReview")
public class ReviewsController {
    @Autowired
    private ReviewsService reviewsService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitReview(
        @RequestParam("review") String reviewText,
        @RequestParam("rating") int rating,
        @RequestParam(value = "images", required = false) MultipartFile[] images) {
        try {
            // 리뷰 처리 로직 호출
            reviewsService.saveReview(reviewText, rating, images);
            return ResponseEntity.ok().body("리뷰가 성공적으로 제출되었습니다.");
        } catch (Exception e) {
            log.error("리뷰 제출 중 에러 발생", e);
            return ResponseEntity.badRequest().body("리뷰 제출에 실패했습니다.");
        }
    }
}
