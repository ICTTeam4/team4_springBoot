package com.saintkream.server.domain.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.auth.mapper.AuthMapper;
import com.saintkream.server.domain.auth.vo.MembersVO;
import com.saintkream.server.domain.auth.vo.UserVO;
import com.saintkream.server.domain.members.mapper.MembersMapper;



@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private MembersMapper membersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO member = authMapper.selectMember(username);
        if (member == null) {
            throw new UsernameNotFoundException("없는 아이디 입니다.");
        }
        return new User(member.getM_id(), member.getPassword(), new ArrayList<>());
    }

    // DB에서 개인 정보 추출,
    public UserVO getUserDetail(String m_id) {
        return authMapper.selectMember(m_id);
    }

    // UserDetails userDetails = userDetailService.loadUserByOAuth2User(oAuth2User,
    // provider);
    public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User, String provider) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        System.out.println(oAuth2User);
        System.out.println(provider);
        // kakao Long 이고 String 변경안됨
        String id = "";
        MembersVO mvo = new MembersVO();
        if (provider.equals("kakao")) {
            Long kakaoId = oAuth2User.getAttribute("id");
            id = String.valueOf(kakaoId);
            mvo.setSns_email_kakao(email);
            mvo.setName(name);
            mvo.setEmail(email); // long형 자료는 지금으로선 의미가 없다고 보고  일단 email씀. 원랜 id가 맞음 (고유번호)
            mvo.setSns_provider("kakao");

        } else if (provider.equals("naver")) {
            id = oAuth2User.getAttribute("id");
            mvo.setSns_email_naver(email);
            mvo.setName(name);
            mvo.setEmail(email);  //마찬가지로 지금은 일단 email씀 원랜 id가 맞음 (고유번호)
            mvo.setSns_provider("naver");
        } else if (provider.equals("google")) {
            id = oAuth2User.getAttribute("email");
            mvo.setSns_email_google(email);
            mvo.setName(name);
            mvo.setEmail(email);
            mvo.setSns_provider("google");
        }
        // 아이디가 존재하면 DB에 있는 것, 아니면 DB에 없는 것
        MembersVO mvo2 = membersMapper.findUserByProvider(mvo);
        if (mvo2 == null) {
            membersMapper.insertSNSMember(mvo);//바로 가입하는법. 소셜로그인한사람은 추후 휴대폰인증은  마이페이지 등에서 따로 필요. 
        }
     
        return new User(mvo.getEmail(), "", new ArrayList<>());
    }
}