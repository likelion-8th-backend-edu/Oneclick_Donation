package com.example.OneclickDonation.post.controller;

import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.CommentService;
import com.example.OneclickDonation.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/post/create")
    public String create() {
        return "post/create";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        model.addAttribute("comment", commentService.commentByPost(id));
        return "/post/view";
    }

    @GetMapping("/post/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        return "/post/edit";
    }

    @GetMapping("/post/{id}/news")
    public String viewNews(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        return "/post/news";
    }

    @GetMapping("/post/{id}/support-amount-target-amount")
    @ResponseBody
    public String getSupportAmountAndTargetAmount(@PathVariable Long id) {
        // 해당 ID에 대한 게시물을 조회합니다.
        PostDto post = postService.readOne(id);

        // 조회된 게시물의 지원 금액과 목표 금액을 가져옵니다.
        Integer supportAmount = post.getSupportAmount();
        Integer targetAmount = post.getTargetAmount();

        // JSON 형식으로 결과를 반환합니다.
        return "{ \"supportAmount\": " + supportAmount + ", \"targetAmount\": " + targetAmount + " }";
    }

    // 기부하기 결제 창
    @GetMapping("/post/{id}/donation")
    public String donation(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        return "toss/payment";
    }
}
