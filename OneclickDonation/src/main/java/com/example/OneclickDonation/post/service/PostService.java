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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;

    /*public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        if(this.postRepository.count() == 0) {
            this.postRepository.saveAll(List.of(
                    Post.builder()
                            .title("어르신에게 따뜻한 봄날을 선물해 주세요!")
                            .postImage("/static/1.png")
                            .description("가정의달 5월이 다가오고 있습니다. 매년 5월 8일은 어버이날로 우리에게 매우 소중한 날 중 하나 입니다. 우리는 어버이 사랑과 감사를 전하는 시간을 가지고, 부모님이 우리 인생에 얼마나 중요한 영향력을 끼쳤는지 되새겨보는 시간을 함께 가지며 가족의 정을 나누고 행복한 시간을 가집니다. 하지만 누구보다 행복한 시간을 보내야할 어버이 중에는 오히려 고독한 하루를 보내는 분들도 있습니다. 의성군 금성면 남OO 어르신은 과거 아내와 사별하고 30년 가까이 홀로 생활하고 있습니다. 자녀들은 자주 오는지 여쭙는 질문에 “자녀들도 사는 게 힘들어서 못 와.”라고 씁쓸한 표정으로 대답하십니다. 그래도 자녀들이 잘 살았으면 좋겠다는 말씀을 덧붙이며 오히려 자식들을 걱정하고 지지해 주시는 모습에 안타까운 마음이 듭니다.")
                            .supportAmount(0)
                            .targetAmount(100000)
                            .startDate("2024-03-29")
                            .endDate("2024-04-20")
                            .status(Status.ING)
                            .build()
            ));
        }
    }*/

    public PostDto create(String title, String description, Integer targetAmount,
                          String imageUrl, String startDate, String endDate) {
        // 게시글 생성을 위해 PostDto 객체를 생성하고 저장
        Post newPost = Post.builder()
                .title(title)
                .description(description)
                .targetAmount(targetAmount)
                .postImage(imageUrl) // 이미지 URL 설정
                .startDate(startDate)
                .endDate(endDate)
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
        post.setStartDate(dto.getStartDate());
        post.setEndDate(dto.getEndDate());
        post.setNews(dto.getNews());
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

