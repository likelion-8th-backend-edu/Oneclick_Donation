package com.example.OneclickDonation.post.controller;

import com.example.OneclickDonation.post.dto.NewsDto;
import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.FileStorageService;
import com.example.OneclickDonation.post.service.NewsService;
import com.example.OneclickDonation.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class NewsController {
    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final NewsService newsServices;

    @GetMapping("/{postId}/newsCreate")
    public String create(@PathVariable Long postId, Model model) {
        PostDto post = postService.readOne(postId);
        model.addAttribute("post", post);
        return "post/newsCreate";
    }

    @PostMapping("/{postId}/newsCreate")
    public String createNews(
            @PathVariable Long postId,
            @RequestParam("newsContent") String newsContent,
            @RequestParam("newsImage") MultipartFile newsImage
    ) {
        String newsImageUrl = fileStorageService.storeFile(newsImage);
        newsServices.create(postId, newsContent, newsImageUrl);
        return String.format("redirect:/post/%d/news", postId);
    }

    @GetMapping("/{postId}/news")
    public String viewNews(@PathVariable Long postId, Model model) {
        PostDto post = postService.readOne(postId);
        model.addAttribute("post", post);
        model.addAttribute("news", newsServices.newsByPost(postId));
        return "post/news";
    }

    @GetMapping("/{postId}/news/edit")
    public String viewEdit(@PathVariable Long postId, Model model) {
        PostDto post = postService.readOne(postId);
        model.addAttribute("post", post);
        model.addAttribute("news", newsServices.newsByPost(postId));
        return "post/newsEdit";
    }
    @PostMapping("/{postId}/news/edit")
    public String editNews(
            @PathVariable Long postId,
            @RequestParam("newsContent") String newsContent,
            @RequestParam(value = "newsImage", required = false) MultipartFile newsImage
    ) {
        String imageUrl = null;
        if (newsImage != null && !newsImage.isEmpty()) {
            imageUrl = fileStorageService.storeFile(newsImage);
        }
        NewsDto existingNews = newsServices.newsByPost(postId);
        newsServices.update(postId,newsContent, imageUrl);
        return String.format("redirect:/post/%d/news", postId);
    }

    @PostMapping("/{postId}/news/delete")
    public String deleteNews(
            @PathVariable Long postId
    ) {
        newsServices.delete(postId);
        return String.format("redirect:/post/%d/news", postId);
    }
}