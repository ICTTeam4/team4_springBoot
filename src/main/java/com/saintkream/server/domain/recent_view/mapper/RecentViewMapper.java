package com.saintkream.server.domain.recent_view.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.recent_view.vo.RecentViewVO;

@Mapper
public interface RecentViewMapper {
     List<RecentViewVO> getRecentlyViewedByMemberId(int memberId);
    void insertRecentlyViewed(RecentViewVO rvo);
    void deleteRecentlyViewed(int recentId);
}
