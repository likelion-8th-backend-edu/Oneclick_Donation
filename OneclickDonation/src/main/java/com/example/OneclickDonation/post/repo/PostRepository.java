package com.example.OneclickDonation.post.repo;

import com.example.OneclickDonation.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
