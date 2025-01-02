package com.saintkream.server.domain.searchitems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.searchitems.vo.SearchItemsVO;

@Mapper
public interface SearchItemsMapper {
    List<SearchItemsVO> searchItems(@Param("keyword") String keyword, @Param("category") String category);
}
