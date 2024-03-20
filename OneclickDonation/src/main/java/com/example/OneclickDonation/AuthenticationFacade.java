package com.example.OneclickDonation;

import com.example.OneclickDonation.member.dto.CustomUserDetails;
import com.example.OneclickDonation.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
// 인증에 관련된 자주사용하는 메서드들을 모아둔 클래스
public class AuthenticationFacade {
    // 인증 반환
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // UserDetails를 구현한 (인증된)사용자 (정보)반환
    public Member extractUser() {
        CustomUserDetails userDetails
                = (CustomUserDetails) getAuth().getPrincipal();
        return userDetails.getMember();
    }
}
