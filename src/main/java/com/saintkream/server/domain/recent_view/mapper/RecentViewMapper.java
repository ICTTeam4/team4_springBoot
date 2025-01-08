package com.saintkream.server.domain.recent_view.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.recent_view.vo.RecentViewVO;

@Mapper
public interface RecentViewMapper {
     List<RecentViewVO> getRecentlyViewedByMemberId(@Param("member_id") String member_id);
   // void insertRecentlyViewed(String member_id, String pwr_id);
   void insertRecentlyViewed(RecentViewVO rvo);
    void deleteRecentlyViewed(@Param("member_id") String member_id, @Param("pwr_id") String pwr_id);

     //void deleteRecentView(@Param("member_id") String member_id, @Param("pwr_id") String pwr_id);
}
