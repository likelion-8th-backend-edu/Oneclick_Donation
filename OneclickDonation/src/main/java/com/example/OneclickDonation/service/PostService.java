package com.example.OneclickDonation.service;

import com.example.OneclickDonation.dto.PostDto;
import com.example.OneclickDonation.entity.Member;
import com.example.OneclickDonation.entity.Post;
import com.example.OneclickDonation.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDto create(PostDto dto) {
        /*// SecurityContextHolder에서 사용자 가져오기
        UserDetails userDetails =
                (UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // 사용자 username 받아오기
        String username = userDetails.getUsername();
        // UserEntity 회수
        UserEntity writer = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));*/
//        Member writer = getUserEntity();
        Post newPost = Post.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();

        // 저장
        return PostDto.fromEntity(postRepository.save(newPost));
    }

//    TODO 나중에 구현
//    private Member getUserEntity() {
//        UserDetails userDetails =
//                (UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//        return postRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
    public PostDto readOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return PostDto.fromEntity(post);
        }

    public PostDto update(Long id, PostDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        return PostDto.fromEntity(postRepository.save(post));
    }

}
