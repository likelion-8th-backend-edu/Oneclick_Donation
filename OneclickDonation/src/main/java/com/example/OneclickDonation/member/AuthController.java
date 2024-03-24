package com.example.OneclickDonation.member;

import com.example.OneclickDonation.jwt.JwtRequestDto;
import com.example.OneclickDonation.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
// 필요한 다른 import문 추가

@RestController
@RequestMapping("/donation")
public class AuthController {

    @Autowired
    private MemberService service; // 실제 서비스 이름에 맞게 변경해주세요.

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody JwtRequestDto dto) {
        String token = String.valueOf(service.signin(dto)); // 로그인 성공 시 토큰 반환. signin 메소드 구현에 따라 다를 수 있음.
        if (token != null) {
            return ResponseEntity.ok().body(Map.of("token", token)); // 토큰을 JSON 형태로 반환
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "로그인 실패")); // 로그인 실패 시 메시지 반환
        }
    }
}