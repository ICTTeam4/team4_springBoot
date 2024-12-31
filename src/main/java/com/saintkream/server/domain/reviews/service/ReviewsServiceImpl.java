package com.saintkream.server.domain.reviews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String IMAGES_DIR = "src/main/resources/static/images";
    // 실제 이미지 저장 경로로 수정 필요

    @Override
    public void saveReview(String reviewText, int rating, MultipartFile[] images) {
        // 리뷰 정보 저장하고 생성된 리뷰 ID 획득
        int reviewId = saveReviewToDatabase(reviewText, rating);

        // 이미지 파일 저장 및 데이터베이스에 경로 저장
        for (MultipartFile file : images) {
            String filePath = saveImageFile(file);
            if (filePath != null) {
                saveImagePath(reviewId, filePath);
            }
        }
    }

    private int saveReviewToDatabase(String reviewText, int rating) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reviews (review_text, rating, created_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reviewText);
            ps.setInt(2, rating);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue(); // 생성된 리뷰 ID 반환
    }

    private String saveImageFile(MultipartFile file) {
        if (file.isEmpty()) return null; // 파일이 비어있으면 null 반환

        try {
            Path directory = Paths.get(IMAGES_DIR);
            if (!Files.exists(directory)) Files.createDirectories(directory);
            Path filePath = directory.resolve(System.currentTimeMillis() + "_" + file.getOriginalFilename());
            file.transferTo(filePath);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    private void saveImagePath(int reviewId, String filePath) {
        String sql = "INSERT INTO review_files (review_id, file_path) VALUES (?, ?)";
        jdbcTemplate.update(sql, reviewId, filePath);
    }
}
