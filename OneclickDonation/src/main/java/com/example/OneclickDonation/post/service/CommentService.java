package com.example.OneclickDonation.post.service;

import com.example.OneclickDonation.post.dto.CommentDto;
import com.example.OneclickDonation.post.entity.Comment;
import com.example.OneclickDonation.post.entity.Post;
import com.example.OneclickDonation.post.repo.CommentRepository;
import com.example.OneclickDonation.post.repo.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    public CommentDto createComment(Long postId, CommentDto dto) {
        Post post = postRepository.findById(postId).orElseThrow();

        Comment newComment = new Comment();
        newComment.setPost(post);
        newComment.setContent(dto.getContent());
        return CommentDto.fromEntity(commentRepository.save(newComment));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
