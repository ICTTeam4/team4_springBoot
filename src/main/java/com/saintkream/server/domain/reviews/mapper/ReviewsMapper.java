package com.saintkream.server.domain.reviews.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewsMapper {    
   List<Integer> getReviewPwr(String member_id);
   List<Integer> getSellReviewPwr(String member_id);
}
