package com.example.OneclickDonation.member.controller;

import com.example.OneclickDonation.jwt.JwtRequestDto;
import com.example.OneclickDonation.member.MemberService;
import com.example.OneclickDonation.member.dto.MemberDto;
import com.example.OneclickDonation.member.dto.MemberUpgradeDto;
import com.example.OneclickDonation.member.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/donation")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService service;

    // 회원가입
    @PostMapping("/signup")
    public MemberDto register(
            @RequestBody RegisterDto dto
    ) {
        return service.register(dto);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(
            @RequestBody JwtRequestDto dto
    ) {
        String token = String.valueOf(service.signin(dto));
        if (token != null) {
           return ResponseEntity.ok().body(service.signin(dto));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "로그인 실패"));
        }
    }

    // 사용자 전환 신청
    @PostMapping("/upgrade-request")
    public void upgradeRequest(
            @RequestBody MemberUpgradeDto dto
    ) {
        service.upgradeRequest(dto);
    }
}