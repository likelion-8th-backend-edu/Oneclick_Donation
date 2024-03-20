package com.example.OneclickDonation.member;

import com.example.OneclickDonation.member.dto.MemberDto;
import com.example.OneclickDonation.member.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return "redirect:/donation/signin";
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

}
