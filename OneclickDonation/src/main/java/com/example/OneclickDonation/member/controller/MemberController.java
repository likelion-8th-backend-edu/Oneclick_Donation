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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String homePage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        // 페이지네이션에 사용할 페이지 크기
        int pageSize = 12;
        Page<PostDto> postPage = postService.readPage(PageRequest.of(page, pageSize));
        model.addAttribute("page", postPage);
        return "member/home";
    }

    @GetMapping("/end")
    public String endPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        // 페이지네이션에 사용할 페이지 크기
        int pageSize = 12;
        Page<PostDto> postPage = postService.readEndPost(PageRequest.of(page, pageSize));
        model.addAttribute("page", postPage);
        return "member/endPost";
    }


    @GetMapping("/donation/signup")
    public String signupPage() {
        return "/member/signup";
    }

    @GetMapping("/donation/signin")
    public String signinPage(){
        return "member/signin";
    }

    @GetMapping("/donation/upgrade-request")
    public String upgradeRequestPage() {
        return "/member/upgradeReq";
    }

}
