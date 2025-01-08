package com.saintkream.server.domain.reviews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.domain.reviews.mapper.ReviewsMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String IMAGES_DIR = "src/main/resources/static/images";
    // 실제 이미지 저장 경로로 수정 필요

    @Override
    public void saveReview(String seller_id, String buyer_id, Integer pwr_id, String content, int rate, MultipartFile[] images, Integer member_id) {
        // 리뷰 데이터베이스 저장
        int reviewId = saveReviewToDatabase(seller_id, buyer_id, pwr_id, content, rate, member_id);

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

    private int saveReviewToDatabase(String seller_id, String buyer_id, int pwr_id, String content, int rate, int member_id) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reviews (seller_id, buyer_id, pwr_id, content, rate, created_at, member_id) VALUES (?, ?, ?, ?, ?, NOW(),?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "review_id" });
            ps.setString(1, seller_id);
            ps.setString(2, buyer_id);
            ps.setInt(3, pwr_id);
            ps.setString(4, content);
            ps.setInt(5, rate);
            ps.setInt(6, member_id);
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

    @Override
    public List<Map<String, Object>> getReviewsByMemberId(Integer member_id) {
        String sql = "SELECT r.review_id, r.content, r.rate, r.created_at, 'mine' as type, r.member_id, " +
                     "f.file_url, f.file_name, " +
                     "m.nickname " + // members 테이블의 nickname 추가
                     "FROM reviews r " +
                     "LEFT JOIN review_file rf ON r.review_id = rf.review_id " +
                     "LEFT JOIN file_table f ON rf.file_id = f.file_id " +
                     "LEFT JOIN members m ON r.member_id = m.member_id " + // members 테이블 조인
                     "WHERE r.member_id = ?";
    
        return jdbcTemplate.query(sql, ps -> {
            ps.setInt(1, member_id);
        }, (rs, rowNum) -> {
            Map<String, Object> review = new HashMap<>();
            review.put("review_id", rs.getInt("review_id"));
            review.put("content", rs.getString("content"));
            review.put("rate", rs.getInt("rate"));
            review.put("created_at", rs.getTimestamp("created_at"));
            review.put("type", rs.getString("type"));
            review.put("member_id", rs.getInt("member_id"));
            review.put("file_url", rs.getString("file_url")); // 이미지 URL 추가
            review.put("file_name", rs.getString("file_name")); // 이미지 파일명 추가
            review.put("nickname", rs.getString("nickname")); // 닉네임 추가
            return review;
        });
    }
    

    @Override
public List<Map<String, Object>> getReviewsByBuyerOrSeller(Integer member_id) {
    String sql = "SELECT r.review_id, r.content, r.rate, r.created_at, r.member_id, " +
                 "CASE " +
                 "  WHEN r.buyer_id = ? THEN 'buyer' " +
                 "  WHEN r.seller_id = ? THEN 'seller' " +
                 "END AS type, " +
                 "f.file_url, f.file_name, " +
                 "m.nickname " + // members 테이블의 nickname 추가
                 "FROM reviews r " +
                 "LEFT JOIN review_file rf ON r.review_id = rf.review_id " +
                 "LEFT JOIN file_table f ON rf.file_id = f.file_id " +
                 "LEFT JOIN members m ON r.member_id = m.member_id " + // members 테이블 조인
                 "WHERE r.buyer_id = ? OR r.seller_id = ?";

    return jdbcTemplate.query(sql, ps -> {
        ps.setInt(1, member_id); // buyer_id 조건
        ps.setInt(2, member_id); // seller_id 조건
        ps.setInt(3, member_id); // buyer_id 필터
        ps.setInt(4, member_id); // seller_id 필터
    }, (rs, rowNum) -> {
        Map<String, Object> review = new HashMap<>();
        review.put("review_id", rs.getInt("review_id"));
        review.put("content", rs.getString("content"));
        review.put("rate", rs.getInt("rate"));
        review.put("created_at", rs.getTimestamp("created_at"));
        review.put("type", rs.getString("type"));
        review.put("member_id", rs.getInt("member_id"));
        review.put("file_url", rs.getString("file_url")); // 이미지 URL 추가
        review.put("file_name", rs.getString("file_name")); // 이미지 파일명 추가
        review.put("nickname", rs.getString("nickname")); // 닉네임 추가
        return review;
    });
}

    @Override
    public List<Integer> getReviewPwr(String member_id) {
        return reviewsMapper.getReviewPwr(member_id);
    }

    @Override
    public List<Integer> getSellReviewPwr(String member_id) {
        return reviewsMapper.getSellReviewPwr(member_id);
    }


    
}
