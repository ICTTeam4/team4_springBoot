package com.saintkream.server.domain.searchitems.controller;

import com.saintkream.server.domain.searchitems.service.SearchItemsService;
import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/searchItems")
public class SearchItemsController {

    @Autowired
    private SearchItemsService searchItemsService;

    // 공통 검색 로직
    private ResponseEntity<?> searchItemsByCategory(String keyword, String category) {
        log.info("검색 요청 - keyword: {}, category: {}", keyword, category);

        if (keyword == null || keyword.trim().isEmpty()) {
            log.warn("검색어가 비어 있습니다.");
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "검색어를 입력해주세요."
            ));
        }

        try {
            List<SearchItemsVO> items = searchItemsService.searchItems(keyword, category);

            if (items.isEmpty()) {
                log.info("검색 결과 없음 - keyword: {}, category: {}", keyword, category);
                return ResponseEntity.ok(Map.of(
                    "status", "empty",
                    "message", "검색 결과가 없습니다.",
                    "items", items
                ));
            }

            log.info("검색 결과: {} 개 항목 - category: {}", items.size(), category);
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "items", items
            ));

        } catch (Exception e) {
            log.error("검색 중 서버 에러 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", "error",
                "message", "서버 에러가 발생했습니다.",
                "details", e.getMessage()
            ));
        }
    }

    // 각 카테고리별 엔드포인트 정의
    @GetMapping("/outerList")
    public ResponseEntity<?> searchOuterList(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, "Outer");
    }

    @GetMapping("/topList")
    public ResponseEntity<?> searchTopList(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, "Top");
    }

    @GetMapping("/bottomList")
    public ResponseEntity<?> searchBottomList(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, "Bottom");
    }

    @GetMapping("/shoesList")
    public ResponseEntity<?> searchShoesList(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, "Shoes");
    }

    @GetMapping("/bagsList")
    public ResponseEntity<?> searchBagsList(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, "Bags");
    }

    @GetMapping("/accessoriesList")
    public ResponseEntity<?> searchAccessoriesList(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, "Accessories");
    }

    // 기본 검색 (카테고리 지정이 없는 경우)
    @GetMapping
    public ResponseEntity<?> searchAllItems(@RequestParam String keyword) {
        return searchItemsByCategory(keyword, null);
    }
}
