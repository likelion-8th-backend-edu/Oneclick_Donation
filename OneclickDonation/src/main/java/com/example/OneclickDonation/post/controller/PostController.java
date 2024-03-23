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
            @RequestParam(value = "postImage", required = false) MultipartFile image
    ) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileStorageService.storeFile(image);
        }
        postService.update(id, new PostDto(title, description, targetAmount, imageUrl, startDate, endDate));
        return "redirect:/post/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/donation";
    }
}
