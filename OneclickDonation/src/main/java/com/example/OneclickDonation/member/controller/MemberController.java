package com.example.OneclickDonation.member.controller;

import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MemberController {
    private final PostService postService;

    @Autowired
    public MemberController(PostService postService) {
        this.postService = postService;
    }

    // 홈 화면 -- 모금 중 리스트 나열된 화면
    @GetMapping("/donation")
    public String homePage(Model model) {
        Page<PostDto> postPage = postService.readPage(PageRequest.of(0, 10));
        model.addAttribute("page", postPage);
        return "member/home";
    }

    // 회원가입
    @GetMapping("/donation/signup")
    public String signupPage() {
        return "/member/signup";
    }

    // 로그인
    @GetMapping("/donation/signin")
    public String signinPage(){
        return "member/signin";
    }

    // 사용자 전환 신청
    @GetMapping("/donation/upgrade-request")
    public String upgradeRequestPage() {
        return "/member/upgradeReq";
    }

}
