package com.saintkream.server.config;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
// SNS에게 사용자 정보 요청을 처리하고, 사용자 정보를 수신한다. OAuth2User 객체 생서
public class CustomerOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomerOAuth2UserService");

        // 부모클래스의 loadUser 메서드를 호출하여 기본 사용자 정보를 가져온다.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 사용자 속성 가져오기;
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 어떤 제공자 인지 알수 있다.(kakao, naver, google)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("하윤서치: 프로바이더" + provider);
        if (provider.equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount == null) {
                throw new OAuth2AuthenticationException("Kakao error");
            }
            String email = (String) kakaoAccount.get("email");

            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            if (properties == null) {
                throw new OAuth2AuthenticationException("Kakao error");
            }
            String name = (String) properties.get("nickname");

            log.info("kakao email : " + email);
            log.info("kakao name : " + name);

            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                    "email", email,
                    "name", name,
                    "id", attributes.get("id")), "email");

        } else if (provider.equals("naver")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            if (response == null) {
                throw new OAuth2AuthenticationException("naver error");
            }
            String name = (String) response.get("name");
            String email = (String) response.get("email");
            
            log.info("naver email : " + email);
            log.info("naver name : " + name);
            return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
                    "email", email,
                    "name", name,
                    "id", response.get("id")), "email");

        } else if (provider.equals("google")) {
            String name = (String) attributes.get("name");
            String email = (String) attributes.get("email");
            String id = (String) attributes.get("sub"); // Google의 고유 사용자 ID

            if (name == null || email == null || id == null) {
                throw new OAuth2AuthenticationException("Google user attributes are missing");
            }

            log.info("google email : " + email);
            log.info("google name : " + name);
            return new DefaultOAuth2User(
                    oAuth2User.getAuthorities(),
                    Map.of(
                            "email", email,
                            "name", name,
                            "id", id),
                    "email");

        }
        return oAuth2User;
    }

}