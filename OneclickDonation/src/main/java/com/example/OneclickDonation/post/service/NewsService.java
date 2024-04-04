package com.example.OneclickDonation.post.service;

import com.example.OneclickDonation.post.dto.NewsDto;
import com.example.OneclickDonation.post.entity.News;
import com.example.OneclickDonation.post.entity.Post;
import com.example.OneclickDonation.post.repo.NewsRepository;
import com.example.OneclickDonation.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final PostRepository postRepository;

    public NewsDto create(Long postId, String newsContent, String imageUrl) {
        // postId에 해당하는 Post 엔티티를 가져옵니다.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        // 해당 게시글의 상태가 ENG인지 확인합니다.
        if (!post.isEng()) {
            throw new IllegalStateException("News can only be created for posts with status ENG");
        }

        // News 엔티티를 생성하고 postId를 설정합니다.
        News news = News.builder()
                .post(post) // postId 대신에 post를 설정합니다.
                .content(newsContent)
                .newsImage(imageUrl)
                .build();

        // 생성된 News 엔티티를 저장하고 이를 DTO로 변환하여 반환합니다.
        return NewsDto.fromEntity(newsRepository.save(news));
    }

    public NewsDto readNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + id));
        return NewsDto.fromEntity(news);
    }

    public NewsDto update(Long postId, String newsContent, String imageUrl) {
        News news = newsRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + postId));

        news.setContent(newsContent);
        news.setNewsImage(imageUrl);

        return NewsDto.fromEntity(newsRepository.save(news));
    }

    public NewsDto newsByPost(Long postId) {
        Optional<News> newsOptional = newsRepository.findByPostId(postId);
        if (newsOptional.isPresent()) {
            return NewsDto.fromEntity(newsOptional.get());
        } else {
            return null; // 뉴스가 없으면 null을 반환하거나, 다른 방식으로 처리할 수 있습니다.
        }
    }

    public void delete(Long postId) {
        // ID를 기반으로 삭제할 게시글을 데이터베이스에서 조회
        News news = newsRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + postId));

        newsRepository.delete(news);
    }

}