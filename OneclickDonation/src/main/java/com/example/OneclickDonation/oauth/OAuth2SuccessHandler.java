package com.example.OneclickDonation.oauth;

import com.example.OneclickDonation.jwt.JwtTokenUtils;
import com.example.OneclickDonation.member.MemberService;
import com.example.OneclickDonation.member.dto.CustomUserDetails;
import com.example.OneclickDonation.member.dto.RegisterDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler
    //인증 성공시 특정 URL로 리다이렉트 하고 싶은 경우
        extends SimpleUrlAuthenticationSuccessHandler {

    //JWT 발급을 위해 JwtTokenUtils
    private final JwtTokenUtils tokenUtils;
    private final MemberService memberService;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2UserService의 반환값이 할당됨
        OAuth2User oAuth2User
                =(OAuth2User) authentication.getPrincipal();



        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        String username
                = String.format("{%s}%s", provider, email);
        String providerId =oAuth2User.getAttribute("id");
        String nickname = oAuth2User.getAttribute("nickname");
        String phone = oAuth2User.getAttribute("phone");
        // 처음으로 이 소셜 로그인으로 로그인을 시도했다.
        UserDetails userDetails;
        try {
            userDetails = memberService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            // 새 사용자 정보 생성을 위한 DTO 생성
            RegisterDto registerDto = RegisterDto.builder()
                    .username(username)
                    .password(providerId) // 비밀번호로 사용할 값
                    .repeatPassword(providerId) // 비밀번호 확인 필드, 동일한 값 사용
                    .nickname(nickname)
                    .phone(phone) // phone,
                    .age(null) // age,
                    .build();

            // register 메소드 호출
            memberService.register(registerDto);

            // 회원가입 후 userDetails 재조회
            userDetails = memberService.loadUserByUsername(username);
        }


        // 데이터베이스에서 사용자 계정 회수
        UserDetails details
                = memberService.loadUserByUsername(username);
        // JWT 생성
        String jwt = tokenUtils.generateToken(details);
        // 어디로 리다이렉트 할지 지정
        String targetUrl = String.format(
                "http://localhost:8080/token/validate?token=%s", jwt
        );
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

