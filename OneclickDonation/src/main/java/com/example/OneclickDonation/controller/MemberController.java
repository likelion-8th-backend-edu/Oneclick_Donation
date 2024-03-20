package com.example.OneclickDonation.controller;

import com.example.OneclickDonation.dto.MemberDto;
import com.example.OneclickDonation.dto.RegisterDto;
import com.example.OneclickDonation.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    // 홈 화면 -- 모금 중 리스트 나열된 화면
    @GetMapping("/home")
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
        return "redirect:/members/home";
    }

    @PostMapping ("/signin")
    public String login(@ModelAttribute MemberDto memberDto) {
        MemberDto loginResult = service.login(memberDto);
        if(loginResult != null){
            return "member/home"; // 홈화면
        } else {
            return "login";
        }
    }
}
