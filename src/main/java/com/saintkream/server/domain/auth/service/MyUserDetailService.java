package com.saintkream.server.domain.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // authmapper가 아닌 membersMapper로 통합. 반드시 필요함. 필수필수필수
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MembersVO member = membersMapper.getMembersByIdEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("없는 아이디 입니다.");
        }
        return new User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }

    // DB에서 개인 정보 추출,
    public UserVO getUserDetail(String email) {
        return authMapper.selectMember(email);
    }

    // UserDetails userDetails = userDetailService.loadUserByOAuth2User(oAuth2User,
    // provider);
    // public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User, String
    // provider) {
    public MembersVO loadUserByOAuth2User(OAuth2User oAuth2User, String provider) {
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
            mvo.setEmail(email); // long형 자료는 지금으로선 의미가 없다고 보고 일단 email씀. 원랜 id가 맞음 (고유번호)
            mvo.setSns_provider("kakao");

        } else if (provider.equals("naver")) {
            id = oAuth2User.getAttribute("id");
            mvo.setSns_email_naver(email);
            mvo.setName(name);
            mvo.setEmail(email); // 마찬가지로 지금은 일단 email씀 원랜 id가 맞음 (고유번호)
            mvo.setSns_provider("naver");
        } else if (provider.equals("google")) {
            id = oAuth2User.getAttribute("email");
            mvo.setSns_email_google(email);
            mvo.setName(name);
            mvo.setEmail(email);
            mvo.setSns_provider("google");
        }
        // DB 조회 (중복 SNS 가입 여부 체크)
        MembersVO mvo2 = membersMapper.getMembersByIdEmail(email);
        if (mvo2 == null) {
            // **신규**: 아직 DB에 등록하지 않고, isNew = true 로 표시
            mvo.setNew(true);
            // DB insert는 회원가입 페이지에서 할 것이라면 여기서 insertX
            return mvo;
        } else {
            // **기존 회원**: isNew = false
            mvo2.setNew(false);
            // 만약 status가 INACTIVE이면 로그인/가입 불가 → 예외를 던짐
            if ("INACTIVE".equals(mvo2.getStatus())) {
                throw new IllegalStateException("해당 계정은 탈퇴 또는 비활성 상태입니다.");
            }

            return mvo2;
        }
    }
}