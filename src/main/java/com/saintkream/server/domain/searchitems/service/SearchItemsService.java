package com.saintkream.server.domain.searchitems.service;

import java.util.List;

import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;

public interface SearchItemsService {
  List<SearchItemsVO> searchItems(String keyword, String category);
}