package com.saintkream.server.domain.recent_view.service;

import java.util.List;

import com.saintkream.server.domain.recent_view.vo.RecentViewVO;

public interface RecentViewService {
    List<RecentViewVO> getRecentlyViewedByMemberId(int memberId);
    void insertRecentlyViewed(RecentViewVO rvo);
    void deleteRecentlyViewed(int recentId);
}
