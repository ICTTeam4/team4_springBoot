package com.saintkream.server.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.saintkream.server.common.util.JwtUtil;
import com.saintkream.server.domain.auth.service.MyUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final MyUserDetailService userDetailService;

    public OAth2AuthenticationSuccessHandler(JwtUtil jwtUtil, MyUserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    // 실제 성공한 다음에 클라이언트로 리다이렉트 해주는 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            log.info("OAth2AuthenticationSuccessHandler");
            // authentication.getPrincipal가 OAuth2User(사용자정보를 가지고 있는객체)타입인지 확인한다.
            // authentication 객체는 현재 사용자의 인증상태
            // getPrincipal() 가지고 있는 사용자 정보를 반환
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                String uri = request.getRequestURI();
                String provider = "";
                if (uri.contains("kakao")) {
                    provider = "kakao";
                } else if (uri.contains("naver")) {
                    provider = "naver";
                } else if (uri.contains("google")) {
                    provider = "google";
                } else {
                    provider = "unknown";
                }

                // 성공 후 토큰을 만들어서 클라이언트에게 리다이렉트 한다.
                // 사용자 정보를 DB에 넣자
                userDetailService.loadUserByOAuth2User(oAuth2User, provider);
                // String token = jwtUtil.generateToken(userDetails);

                String id = null;
                String email = "unknown";
                String name = "unknown";

                // ID 필드 처리
                if (provider.equals("google")) {
                    id = oAuth2User.getAttribute("email"); // Google은 'sub'을 ID로 사용. 그러나 provider 구분위해서  sub 빼고 이메일로 대체하자.
                    email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email") : "unknown";
                    name = oAuth2User.getAttribute("name") != null ? oAuth2User.getAttribute("name") : "unknown";
                }  else if (provider.equals("kakao")) {
                    Long kakaoId = oAuth2User.getAttribute("id"); // Kakao는 'id'를 Long으로 반환
                    id = kakaoId != null ? String.valueOf(kakaoId) : null;
                    email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email") : "unknown";
                    name = oAuth2User.getAttribute("name") != null ? oAuth2User.getAttribute("name") : "unknown";
                } else if (provider.equals("naver")) {
                    id = oAuth2User.getAttribute("id"); // 네이버는 'id'가 최상위에 존재
                    email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email") : "unknown";
                    name = oAuth2User.getAttribute("name") != null ? oAuth2User.getAttribute("name") : "unknown";

                    if (id == null) {
                        throw new IllegalArgumentException("Naver ID is missing in the OAuth2User attributes");
                    }
                }

                // ID가 없으면 에러 처리
                if (id == null) {
                    throw new IllegalArgumentException(
                            "ID is missing in the OAuth2User attributes for provider: " + provider);
                }
                // Google, Kakao 공통 필드 처리
                if (provider.equals("google") || provider.equals("kakao")) {
                    email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email") : "unknown";
                    name = oAuth2User.getAttribute("name") != null ? oAuth2User.getAttribute("name") : "unknown";
                }

                String token = jwtUtil.generateToken(id);

                String redirectUrl = String.format(
                        "http://localhost:3000/login?token=%s&username=%s&name=%s&email=%s",
                        URLEncoder.encode(token, StandardCharsets.UTF_8),
                        URLEncoder.encode(id, StandardCharsets.UTF_8),
                        URLEncoder.encode(name, StandardCharsets.UTF_8),
                        URLEncoder.encode(email, StandardCharsets.UTF_8));

                response.sendRedirect(redirectUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/login?error");
        }
    }
}