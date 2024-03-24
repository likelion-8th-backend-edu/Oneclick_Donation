package com.example.OneclickDonation.member;

import com.example.OneclickDonation.jwt.JwtRequestDto;
import com.example.OneclickDonation.jwt.JwtResponseDto;
import com.example.OneclickDonation.member.dto.MemberDto;
import com.example.OneclickDonation.member.dto.MemberUpgradeDto;
import com.example.OneclickDonation.member.dto.RegisterDto;
import com.example.OneclickDonation.member.entity.MemberUpgrade;
import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("donation")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;
    private final PostService postService;

    // 홈 화면 -- 모금 중 리스트 나열된 화면
    @GetMapping
    public String homePage(Model model, Pageable pageable) {
        Page<PostDto> page = postService.readPage(PageRequest.of(0, 10));
        model.addAttribute("page", page);
        model.addAttribute("selected", null);
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
//    @PostMapping("/signin")
//    public String login(@ModelAttribute MemberDto memberDto) {
//        MemberDto loginResult = service.login(memberDto);
//        if(loginResult != null){
//            return "redirect:/donation";
//        } else {
//            return "redirect:/donation/signin";
//        }
//    }

//    @PostMapping("/signin")
//    public String signIn(JwtRequestDto dto) {
//        service.signin(dto);
//        return "redirect:/donation";
//    }

    // 마이페이지에서 신청 버튼 누르면 해당 엔드포인트로 이등 후 폼으로 이동
    @GetMapping("/upgrade-request")
    public String upgradeRequestPage() {
        return "/member/upgradeReq";
    }
    @PostMapping("/upgrade-request")
    public String  upgradeRequest(MemberUpgradeDto dto) {
        service.upgradeRequest(dto);
        return "redirect:/donation"; // 나중에 마이페이지로 이동하기로 변경
    }
}
