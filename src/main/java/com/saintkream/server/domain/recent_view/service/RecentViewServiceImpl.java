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
    public List<RecentViewVO> getRecentlyViewedByMemberId(String member_id) {
        return recentViewMapper.getRecentlyViewedByMemberId(member_id);
    }

    // @Override
    // public void insertRecentlyViewed(String member_id, String pwr_id) {
    //     if (member_id == null || member_id.isEmpty()) {
    //         throw new IllegalArgumentException("member_id는 null이 될 수 없습니다.");
    //     }
    //     if (pwr_id == null || pwr_id.isEmpty()) {
    //         throw new IllegalArgumentException("pwr_id는 null이 될 수 없습니다.");
    //     }
    //     recentViewMapper.insertRecentlyViewed(member_id, pwr_id);
    // }
    @Override
    public void insertRecentlyViewed(RecentViewVO rvo) {
      recentViewMapper.insertRecentlyViewed(rvo);
    }




    @Override
    public void deleteRecentlyViewed(String member_id, String pwr_id) {
        recentViewMapper.deleteRecentlyViewed(member_id, pwr_id);
    }

    @Override
    public List<RecentViewVO> getRecentView(String member_id) {
        return recentViewMapper.getRecentlyViewedByMemberId(member_id);
    }

    // @Override
    // public void deleteRecentView(String member_id, String pwr_id) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'deleteRecentView'");
    // }
}
