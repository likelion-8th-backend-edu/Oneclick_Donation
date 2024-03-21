package com.example.OneclickDonation.post.service;

import com.example.OneclickDonation.Enum.Status;
import com.example.OneclickDonation.post.repo.PostRepository;
import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;

    public PostDto create(String title, String description, Integer targetAmount, MultipartFile image) {
        String imageUrl = fileStorageService.storeFile(image); // 파일 저장 및 URL 획득
        // 게시글 생성을 위해 PostDto 객체를 생성하고 저장
        Post newPost = Post.builder()
                .title(title)
                .description(description)
                .targetAmount(targetAmount)
                .postImage(imageUrl) // 이미지 URL 설정
                .build();
        // 생성된 게시글을 데이터베이스에 저장하고 해당 게시글의 ID를 반환
        return PostDto.fromEntity(postRepository.save(newPost));
    }

    // 홈페이지 모금 중의 전체 모금
    public Page<PostDto> readPage(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByStatus(Status.ING, pageable);
        return posts.map(PostDto::fromEntity);
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
        // ID를 기반으로 수정할 게시글을 데이터베이스에서 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        // 수정된 정보로 게시글 엔티티 업데이트
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setTargetAmount(dto.getTargetAmount());
        post.setPostImage(dto.getPostImage());
        // 업데이트된 게시글을 저장하고 PostDto로 변환하여 반환
        return PostDto.fromEntity(postRepository.save(post));
    }

    public void delete(Long id) {
        // ID를 기반으로 삭제할 게시글을 데이터베이스에서 조회하고 삭제
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        postRepository.delete(post);
    }
}

