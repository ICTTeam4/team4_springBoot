package com.saintkream.server.domain.searchitems.controller;

import com.saintkream.server.domain.searchitems.service.SearchItemsService;
import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;
import com.saintkream.server.domain.auth.vo.DataVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/searchItems")
public class SearchItemsController {

    @Autowired
    private SearchItemsService searchItemsService;

    /**
     * 공통 검색 로직 처리
     */
    private DataVO processSearchRequest(String keyword, String category) {
        DataVO dataVO = new DataVO();

        log.info("검색 요청 실행 - keyword: '{}', category: '{}'", keyword, category);

        if (keyword == null || keyword.trim().isEmpty()) {
            log.warn("검색어가 비어 있습니다.");
            dataVO.setSuccess(false);
            dataVO.setMessage("검색어를 입력해주세요.");
            dataVO.setData(null);
            return dataVO;
        }

        try {
            List<SearchItemsVO> items = searchItemsService.searchItems(keyword, category);

            if (items.isEmpty()) {
                log.info("검색 결과 없음 - keyword: '{}', category: '{}'", keyword, category);
                dataVO.setSuccess(true);
                dataVO.setMessage("검색 결과가 없습니다.");
                dataVO.setData(items);
            } else {
                log.info("검색 성공 - {} 개 항목 반환", items.size());
                dataVO.setSuccess(true);
                dataVO.setMessage("검색 성공");
                dataVO.setData(items);
            }

        } catch (Exception e) {
            log.error("검색 처리 중 오류 발생", e);
            dataVO.setSuccess(false);
            dataVO.setMessage("서버 내부 오류가 발생했습니다.");
            dataVO.setData(null);
        }

        log.info("메서드의 마지막 응답 데이터: {}", dataVO);
        return dataVO;
    }

    /**
     * 1차 검색 요청 처리
     */
    @GetMapping("/itemSearchResult")
    public DataVO searchItems(@RequestParam String keyword, @RequestParam(required = false) String category) {
        log.info("일반 검색 요청 - keyword: '{}', category: '{}'", keyword, category);
        return processSearchRequest(keyword, category);
    }

    /**
     * 카테고리별 검색 요청 처리
     */
    @GetMapping("/{category}List")
    public DataVO searchCategoryList(@RequestParam String keyword, @PathVariable String category) {
        log.info("{} 카테고리 검색 요청 - keyword: '{}'", category, keyword);
        return processSearchRequest(keyword, category);
    }
}
