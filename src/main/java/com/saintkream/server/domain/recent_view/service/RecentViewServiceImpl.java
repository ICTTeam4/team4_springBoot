package com.saintkream.server.domain.recent_view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.recent_view.mapper.RecentViewMapper;
import com.saintkream.server.domain.recent_view.vo.RecentViewVO;

@Service
public class RecentViewServiceImpl implements RecentViewService {


    @Autowired
     private RecentViewMapper recentViewMapper;

    @Override
    public List<RecentViewVO> getRecentlyViewedByMemberId(int memberId) {
        return recentViewMapper.getRecentlyViewedByMemberId(memberId);
    }

    @Override
    public void insertRecentlyViewed(RecentViewVO rvo) {
      recentViewMapper.insertRecentlyViewed(rvo);
    }

    @Override
    public void deleteRecentlyViewed(int recentId) {
        recentViewMapper.deleteRecentlyViewed(recentId);
    }
}
