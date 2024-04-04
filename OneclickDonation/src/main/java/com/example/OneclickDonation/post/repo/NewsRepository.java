package com.example.OneclickDonation.post.repo;

import com.example.OneclickDonation.post.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByPostId(Long postId);
}
