package com.example.OneclickDonation.post.controller;

import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.CommentService;
import com.example.OneclickDonation.post.service.FileStorageService;
import com.example.OneclickDonation.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final CommentService commentService;
    @GetMapping("/create")
    public String create() {
        return "post/create";
    }

    @PostMapping("/create")
    public String createPost(
            @RequestParam("postTitle") String title,
            @RequestParam("postContents") String description,
            @RequestParam("postTargetAmount") Integer targetAmount,
            @RequestParam("postImage") MultipartFile postImage,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        Long newId = postService.create(title, description, targetAmount, postImage, startDate, endDate).getId();
        return String.format("redirect:/post/%d", newId);
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        model.addAttribute("comment", commentService.commentByPost(id));
        return "/post/view";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        return "/post/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(
            @PathVariable Long id,
            @RequestParam("postTitle") String title,
            @RequestParam("postContents") String description,
            @RequestParam("postTargetAmount") Integer targetAmount,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("news") String news,
            @RequestParam(value = "postImage", required = false) MultipartFile image
    ) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileStorageService.storeFile(image);
        }
        postService.update(id, new PostDto(title, description, targetAmount, imageUrl, startDate, endDate, news));
        return "redirect:/post/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/donation";
    }

    @GetMapping("/{id}/news")
    public String viewNews(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
        return "/post/news";
    }

    @GetMapping("/{id}/support-amount-target-amount")
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
}
