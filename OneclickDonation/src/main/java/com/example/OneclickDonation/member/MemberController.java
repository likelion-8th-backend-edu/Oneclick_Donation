package com.example.OneclickDonation.member;

import com.example.OneclickDonation.jwt.JwtRequestDto;
import com.example.OneclickDonation.jwt.JwtResponseDto;
import com.example.OneclickDonation.member.dto.MemberDto;
import com.example.OneclickDonation.member.dto.MemberUpgradeDto;
import com.example.OneclickDonation.member.dto.RegisterDto;
import com.example.OneclickDonation.member.entity.MemberUpgrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("donation")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    // 홈 화면 -- 모금 중 리스트 나열된 화면
    @GetMapping
    public String homePage() {
        return "member/home";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String register(RegisterDto dto) {
        service.register(dto);
        return "redirect:/donation";
    }

    @GetMapping("/signin")
    public String signinPage(){
        return "member/signin";
    }

    //로그인
    @PostMapping("/signin")
    public String login(@ModelAttribute MemberDto memberDto) {
        MemberDto loginResult = service.login(memberDto);
        if(loginResult != null){
            return "redirect:/donation";
        } else {
            return "redirect:/donation/signin";
        }
    }

    /*@PostMapping("/signin")
    public String signIn(JwtRequestDto dto) {
        service.signin(dto);
        return "redirect:/donation";
    }*/

    // 마이페이지에서 신청 버튼 누르면 해당 엔드포인트로 이등 후 폼으로 이동
    @GetMapping("/upgrade-request")
    public String upgradeRequestPage() {
        log.info("단체 신청하기");
        return "/member/upgradeReq";
    }
    @PutMapping("/upgrade-request")
    public void upgradeRequest(MemberUpgradeDto dto) {
        service.upgradeRequest(dto);
    }
}
