package com.example.OneclickDonation.post.service;

import com.example.OneclickDonation.Enum.Status;
import com.example.OneclickDonation.member.entity.Member;
import com.example.OneclickDonation.member.repo.MemberRepository;
import com.example.OneclickDonation.post.repo.PostRepository;
import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;
    private final MemberRepository memberRepository;


    public PostDto create(String title, String description, Integer targetAmount,
                          String imageUrl, String startDate, String endDate, String organization) {

        // 게시글 생성을 위해 PostDto 객체를 생성하고 저장
        LocalDate today = LocalDate.now();
        LocalDate parsedStartDate = LocalDate.parse(startDate);

        if (parsedStartDate.isBefore(today)) {
            // 모금 시작 날짜가 오늘 날짜보다 과거인 경우 오늘 날짜로 설정
            startDate = today.toString();
        }

        // 현재 로그인한 사용자의 조직 가져오기

        // 게시글 생성을 위해 PostDto 객체를 생성하고 저장
        Post newPost = Post.builder()
                .title(title)
                .description(description)
                .targetAmount(targetAmount)
                .postImage(imageUrl) // 이미지 URL 설정
                .startDate(startDate)
                .endDate(endDate)
                .organization(organization)
                .build();
        newPost.beforeUpdate();
        // 생성된 게시글을 데이터베이스에 저장하고 해당 게시글의 ID를 반환
        return PostDto.fromEntity(postRepository.save(newPost));
    }

    // 홈페이지 모금 중의 전체 모금
    public Page<PostDto> readPage(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByStatus(Status.ING, pageable);
        return posts.map(PostDto::fromEntity);
    }

    // 모금 종료 게시글
    public Page<PostDto> readEndPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAllByStatus(Status.END, pageable);
        return posts.map(PostDto::fromEntity);
    }


//    //    TODO 나중에 구현


    //    private String getMemberOrganization() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String username;
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = (String) principal;
//        }
//
//        // 사용자가 속한 조직 정보 확인
//        Member member = memberRepository.findByOrganization(username)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        // 조직 정보를 로깅하여 확인
//        String organization = member.getOrganization();
//        System.out.println("User's organization: " + organization);
//
//        return organization;
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

        // 게시글 생성을 위해 PostDto 객체를 생성하고 저장
        LocalDate today = LocalDate.now();
        LocalDate parsedStartDate = LocalDate.parse(dto.getStartDate());

        if (parsedStartDate.isBefore(today)) {
            // 모금 시작 날짜가 오늘 날짜보다 과거인 경우 오늘 날짜로 설정
            post.setStartDate(today.toString());
        }

        // 수정된 정보로 게시글 엔티티 업데이트
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setTargetAmount(dto.getTargetAmount());
        post.setPostImage(dto.getPostImage());
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setOrganization(dto.getOrganization());
        post.beforeUpdate();
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

