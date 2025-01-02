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
@CrossOrigin(origins = "http://localhost:3000") // 프론트엔드 주소
@RequestMapping("/api/searchItems")
public class SearchItemsController {

    @Autowired
    private SearchItemsService searchItemsService;

    /**
     * 공통 검색 로직 처리
     */
    private ResponseEntity<?> processSearchRequest(String keyword, String category) {
        log.info("검색 요청 실행 - keyword: '{}', category: '{}'", keyword, category); // 전달된 값 확인
    
        // 기존 로직 유지
        if (keyword == null || keyword.trim().isEmpty()) {
            log.warn("검색어가 비어 있습니다.");
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "검색어를 입력해주세요."
            ));
        }
    
        try {
            List<SearchItemsVO> items = searchItemsService.searchItems(keyword, category);
    
            log.info("Mapper 호출 후 반환된 결과: {}", items); // Mapper에서 반환된 결과 로그
    
            if (items.isEmpty()) {
                log.info("검색 결과 없음 - keyword: '{}', category: '{}'", keyword, category);
                return ResponseEntity.ok(Map.of(
                    "status", "empty",
                    "message", "검색 결과가 없습니다.",
                    "items", items
                ));
            }
    
            log.info("검색 성공 - {} 개 항목 반환 - category: '{}'", items.size(), category);
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

    /**
     * 카테고리별 검색 요청 처리
     */
    @GetMapping
    public ResponseEntity<?> searchItems(@RequestParam String keyword, @RequestParam(required = false) String category) {
        return processSearchRequest(keyword, category);
    }

    /**
     * /itemSearchResult 요청 처리
     */
    @GetMapping("/itemSearchResult")
    public ResponseEntity<?> searchAllItems(@RequestParam String keyword) {
      log.info("추가 검색 요청 - keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, null); // 카테고리 지정 없이 검색
    }

    /**
     * 카테고리별 엔드포인트 (유지보수성 향상)
     */
    @GetMapping("/outerList")
    public ResponseEntity<?> searchOuterList(@RequestParam String keyword) {
        log.info("========아웃리스트로 잘 넘어왔습니다.!!!!- keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, "아우터");
    }

    @GetMapping("/topList")
    public ResponseEntity<?> searchTopList(@RequestParam String keyword) {
        log.info("========상의리스트로 잘 넘어왔습니다.!!!!- keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, "상의");
    }

    @GetMapping("/bottomList")
    public ResponseEntity<?> searchBottomList(@RequestParam String keyword) {
        log.info("========하의리스트로 잘 넘어왔습니다.!!!!- keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, "하의");
    }

    @GetMapping("/shoesList")
    public ResponseEntity<?> searchShoesList(@RequestParam String keyword) {
        log.info("========신발리스트로 잘 넘어왔습니다.!!!!- keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, "신발");
    }

    @GetMapping("/bagsList")
    public ResponseEntity<?> searchBagsList(@RequestParam String keyword) {
        log.info("========가방리스트로 잘 넘어왔습니다.!!!!- keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, "가방");
    }

    @GetMapping("/accessoriesList")
    public ResponseEntity<?> searchAccessoriesList(@RequestParam String keyword) {
        log.info("========패션잡화리스트로 잘 넘어왔습니다.!!!!- keyword: {}, category: {}", keyword);
        return processSearchRequest(keyword, "패션잡화");
    }
}
