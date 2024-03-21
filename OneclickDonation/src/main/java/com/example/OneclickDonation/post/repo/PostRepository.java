package com.example.OneclickDonation.post.repo;

import com.example.OneclickDonation.Enum.Status;
import com.example.OneclickDonation.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByStatus(Status status, Pageable pageable);
}
