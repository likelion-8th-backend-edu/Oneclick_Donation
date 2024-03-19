package com.example.OneclickDonation.controller;

import com.example.OneclickDonation.dto.PostDto;
import com.example.OneclickDonation.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/create")
    public String create() {
        return "/post/create";
    }
    @PostMapping("/create")
    public String createPost(
            @RequestParam("postTitle") String title,
            @RequestParam("postContents") String description
    ) {
        postService.create(new PostDto(title, description));
        return "redirect:/z"; // 저장 후에 리다이렉트
    }
}
