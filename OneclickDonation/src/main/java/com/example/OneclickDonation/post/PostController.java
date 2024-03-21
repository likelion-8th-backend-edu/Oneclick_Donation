package com.example.OneclickDonation.post;

import com.example.OneclickDonation.post.dto.PostDto;
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
    @GetMapping("/create")
    public String create() {
        return "post/create";
    }

    @PostMapping("/create")
    public String createPost(
            @RequestParam("postTitle") String title,
            @RequestParam("postContents") String description,
            @RequestParam("postTargetAmount") Integer targetAmount,
            @RequestParam("postImage") MultipartFile postImage
    ) {
        Long newId = postService.create(title, description, targetAmount, postImage).getId();
        return String.format("redirect:/post/%d", newId);
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDto post = postService.readOne(id);
        model.addAttribute("post", post);
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
            @RequestParam("title") String title,
            @RequestParam("contents") String description,
            @RequestParam("targetAmount") Integer targetAmount,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            // 이미지가 업로드되었을 경우에만 저장하고 이미지 URL을 얻어옴
            imageUrl = fileStorageService.storeFile(image);
        }
        postService.update(id, new PostDto(title, description, targetAmount, imageUrl));
        return "redirect:/post/" + id;
    }

    @PostMapping("/{id}/delete/")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/post/create";
    }
}
