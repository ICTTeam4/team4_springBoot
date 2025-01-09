package com.saintkream.server.domain.reviews.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewsService {
 public void saveReview(String seller_id, String buyer_id, Integer pwr_id, String content, int rate, MultipartFile[] images,Integer member_id);
 public List<Map<String, Object>> getReviewsByMemberId(Integer member_id);
 public List<Map<String, Object>> getReviewsByBuyerOrSeller(Integer member_id);
 public List<Integer> getReviewPwr(String member_id);
 public List<Integer> getSellReviewPwr(String member_id);
}
