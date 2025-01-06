package com.saintkream.server.domain.recent_view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.recent_view.service.RecentViewService;
import com.saintkream.server.domain.recent_view.vo.RecentViewVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/recent-view")
public class RecentViewController {
    
    @Autowired
    private RecentViewService recentViewService;


    @GetMapping("/{memberId}")
    public List<RecentViewVO> getRecentlyViewedByMemberId (@PathVariable int memberId) {
        return recentViewService.getRecentlyViewedByMemberId(memberId);
    }

     @PostMapping
    public void insertRecentlyViewed(@RequestBody RecentViewVO rvo) {
        recentViewService.insertRecentlyViewed(rvo);
    }

    @DeleteMapping("/{recentId}")
    public void deleteRecentlyViewed(@PathVariable int recentId) {
        recentViewService.deleteRecentlyViewed(recentId);
    }
    



}
