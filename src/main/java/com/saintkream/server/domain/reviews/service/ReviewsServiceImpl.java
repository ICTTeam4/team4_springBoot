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
import java.util.UUID;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String IMAGES_DIR = "src/main/resources/static/images";
    // 실제 이미지 저장 경로로 수정 필요

    @Override
    public void saveReview(String content, int rate, MultipartFile[] images) {
        // 리뷰 데이터베이스 저장
        int reviewId = saveReviewToDatabase(content, rate);

        // 이미지를 선택한 경우에만 처리
        if (images != null && images.length > 0) {
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    String uniqueFileName = saveImageFile(file);
                    String fileUrl = "/images/" + uniqueFileName; // URL로 변환
                    int fileId = saveFileToDatabase(fileUrl, uniqueFileName);
                    saveReviewFileMapping(reviewId, fileId);
                }
            }
        }
        // 이미지를 업로드하지 않은 경우에도 리뷰 저장이 완료됨
    }

    private int saveReviewToDatabase(String content, int rate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reviews (content, rate, created_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "review_id" });
            ps.setString(1, content);
            ps.setInt(2, rate);
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("Failed to retrieve review ID.");
        }
        return keyHolder.getKey().intValue();
    }

    private String saveImageFile(MultipartFile file) {
        try {
            Path directory = Paths.get(IMAGES_DIR);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = directory.resolve(uniqueFileName);
            file.transferTo(filePath);
            return uniqueFileName; // 반환값은 파일명만
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    private int saveFileToDatabase(String fileUrl, String fileName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO file_table (file_url, file_name) VALUES (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "file_id" });
            ps.setString(1, fileUrl); // file_url에 저장
            ps.setString(2, fileName); // file_name에 저장
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("Failed to retrieve file ID.");
        }
        return keyHolder.getKey().intValue();
    }

    private void saveReviewFileMapping(int reviewId, int fileId) {
        String sql = "INSERT INTO review_file (review_id, file_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, reviewId, fileId);
    }
}
