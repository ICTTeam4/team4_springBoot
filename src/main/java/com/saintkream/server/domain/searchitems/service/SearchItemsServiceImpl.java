package com.saintkream.server.domain.searchitems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.searchitems.mapper.SearchItemsMapper;
import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchItemsServiceImpl implements SearchItemsService {

  @Autowired
  private SearchItemsMapper searchItemsMapper;

  @Override
  public List<SearchItemsVO> searchItems(String keyword, String category) {
    List<SearchItemsVO> results = searchItemsMapper.searchItems(keyword, category);
    log.info("Mapper에서 반환된 결과: {}", results);
    return searchItemsMapper.searchItems(keyword, category);
  }
}
