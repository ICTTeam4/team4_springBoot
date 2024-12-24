package com.saintkream.server.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.common.util.JwtUtil;
import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.auth.vo.MembersVO;
import com.saintkream.server.domain.auth.vo.UserVO;



@Service
public class AuthService {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    public DataVO authenticate(MembersVO mvo) {
        DataVO dataVO = new DataVO();
        try {

            // 실제 DB
            UserVO uvo = myUserDetailService.getUserDetail(mvo.getEmail());
            String jwt = jwtUtil.generateToken(uvo.getM_id());

            dataVO.setSuccess(true);
            dataVO.setToken(jwt);
            dataVO.setUserDetails(uvo);
            return dataVO;
        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage(e.getMessage());
            return dataVO;
        }
    }

}