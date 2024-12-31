package com.saintkream.server.domain.reviews.service;

import org.springframework.web.multipart.MultipartFile;

public interface ReviewsService {
 public void saveReview(String reviewText, int rating, MultipartFile[] images);
}
