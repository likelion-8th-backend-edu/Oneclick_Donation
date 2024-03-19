package com.example.OneclickDonation.controller;

import com.example.OneclickDonation.dto.MemberDto;
import com.example.OneclickDonation.dto.RegisterDto;
import com.example.OneclickDonation.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String register( RegisterDto dto) {
        service.register(dto);
        return "redirect:/members/signin";
    }

    @PostMapping ("/signin")
    public String login(@ModelAttribute MemberDto memberDto) {
        MemberDto loginResult = service.login(memberDto);
        if(loginResult != null){
            return "home"; // 홈화면
        } else {
            return "login";
        }
    }
}
