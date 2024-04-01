package com.example.OneclickDonation.post.controller;

import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.CommentService;
import com.example.OneclickDonation.post.service.FileStorageService;
import com.example.OneclickDonation.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final CommentService commentService;

    @PostMapping("/create")
    public String createPost(
            @RequestParam("postTitle") String title,
            @RequestParam("postContents") String description,
            @RequestParam("postTargetAmount") Integer targetAmount,
            @RequestParam("postImage") MultipartFile postImage,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        // 이미지 파일의 저장 경로를 얻어옵니다.
        String imageUrl = fileStorageService.storeFile(postImage);
        // 생성된 게시글의 ID를 얻어옵니다.
        Long newId = postService.create(title, description, targetAmount, imageUrl, startDate, endDate).getId();
        return String.format("redirect:/post/%d", newId);
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
}
