package com.saintkream.server.domain.searchitems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;

@Mapper
public interface SearchItemsMapper {
    // 키워드와 카테고리를 기반으로 검색 결과 반환
    List<SearchItemsVO> searchItems(@Param("keyword") String keyword, @Param("category") String category);
}
