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
        log.info("검색 요청 실행 - keyword: '{}', category: '{}'", keyword, category);

        // Mapper에서 검색 결과를 가져옵니다.
        List<SearchItemsVO> results = searchItemsMapper.searchItems(keyword, category);

        if (results.isEmpty()) {
            log.info("검색 결과가 없습니다. keyword: {}, category: {}", keyword, category);
        } else {
            log.info("검색 성공 - 결과 항목 수: {}", results.size());
            for (SearchItemsVO item : results) {
                log.debug("상품 ID: {}, 파일 리스트: {}", item.getPwr_id(), item.getFileList());
            }
        }

        return results;
    }

    @Override
    public List<SearchItemsVO> getCategoryList(String category) {
        log.info("요청 실행 - category: '{}'", category);

        // Mapper에서 검색 결과를 가져옵니다.
        List<SearchItemsVO> results = searchItemsMapper.getCategoryList(category);

        if (results.isEmpty()) {
            log.info("결과가 없습니다. keyword: {}, category: {}", category);
        } else {
            log.info("성공 - 결과 항목 수: {}", results.size());
            for (SearchItemsVO item : results) {
                log.debug("상품 ID: {}, 파일 리스트: {}", item.getPwr_id(), item.getFileList());
            }
        }

        return results;
    }

    @Override
    public List<SearchItemsVO> getSubCategoryList(String sub_category) {
        log.info("요청 실행 - sub_category: '{}'", sub_category);

        // Mapper에서 검색 결과를 가져옵니다.
        List<SearchItemsVO> results = searchItemsMapper.getSubCategoryList(sub_category);

        if (results.isEmpty()) {
            log.info("결과가 없습니다. sub_category: {}", sub_category);
        } else {
            log.info("성공 - 결과 항목 수: {}", results.size());
            for (SearchItemsVO item : results) {
                log.debug("상품 ID: {}, 파일 리스트: {}", item.getPwr_id(), item.getFileList());
            }
        }

        return results;
    }
}