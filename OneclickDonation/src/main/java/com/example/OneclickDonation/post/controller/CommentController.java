package com.example.OneclickDonation.post.controller;

import com.example.OneclickDonation.post.dto.CommentDto;
import com.example.OneclickDonation.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public String createComment(
            @PathVariable("postId")
            Long postId,
            @RequestParam("content")
            String content
    ) {
        commentService.createComment(postId, new CommentDto(content));
        return String.format("redirect:/post/%d", postId);
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteComment(commentId);
        return "redirect:/post/" + postId;
    }
}
