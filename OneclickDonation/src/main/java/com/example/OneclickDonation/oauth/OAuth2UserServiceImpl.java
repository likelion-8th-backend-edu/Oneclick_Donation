package com.example.OneclickDonation.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OAuth2UserServiceImpl
        extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        Map<String, Object> attributes = new HashMap<>();
        String nameAttribute = "";

//        // Kakao 아이디로 로그인
//        if (registrationId.equals("kakao")) {
//            log.info(oAuth2User.getAttributes().toString());
//            // Kakao에서 받아온 정보
//            attributes.put("provider", "kakao");
//            attributes.put("id", oAuth2User.getAttribute("id"));
//            Map<String, Object> kakaoAccount
//                    = oAuth2User.getAttribute("kakao_account");
//            attributes.put("email", kakaoAccount.get("email"));
//            Map<String, Object> kakaoProfile
//                    = (Map<String, Object>) kakaoAccount.get("profile");
//            attributes.put("nickname", kakaoProfile.get("nickname"));
//            attributes.put("profileImg", kakaoProfile.get("profile_image_url"));
//            nameAttribute = "email";
//        }

        // Naver 아이디로 로그인
        if (registrationId.equals("naver")) {
            // Naver에서 받아온 정보
            attributes.put("provider", "naver");

            Map<String, Object> responseMap
                    // 네이버가 반환한 JSON에서 response를 회수
                    = oAuth2User.getAttribute("response");
            attributes.put("id", responseMap.get("id"));
            attributes.put("email", responseMap.get("email"));
            attributes.put("nickname", responseMap.get("nickname"));
            attributes.put("name", responseMap.get("name"));
            attributes.put("profileImg", responseMap.get("profile_image"));
            nameAttribute = "email";
        }
        log.info(attributes.toString());
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                nameAttribute
        );
    }
}