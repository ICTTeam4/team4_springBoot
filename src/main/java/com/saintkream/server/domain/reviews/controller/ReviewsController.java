package com.saintkream.server.domain.reviews.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("/submit")
    public ResponseEntity<?> submitReview(
        @RequestParam String content,
        @RequestParam int rate,
        @RequestParam(required = false) MultipartFile[] images) {
  
  try {
        // 이미지가 없더라도 예외가 발생하지 않도록 서비스 호출
        reviewsService.saveReview(content, rate, images);
        return ResponseEntity.ok().body(Map.of(
            "success", true,
            "message", "Review submitted successfully"
        ));
    } catch (Exception e) {
        log.error("Error submitting review", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "success", false,
            "message", "Failed to submit review: " + e.getMessage()
        ));
    }
}
}