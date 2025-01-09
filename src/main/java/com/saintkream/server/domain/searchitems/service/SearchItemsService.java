package com.saintkream.server.domain.searchitems.service;

import java.util.List;

import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;

public interface SearchItemsService {
    // 검색 및 파일 리스트를 포함한 데이터 반환
    List<SearchItemsVO> searchItems(String keyword, String category);
    List<SearchItemsVO> getCategoryList(String category);
    List<SearchItemsVO> getSubCategoryList(String sub_category);
}