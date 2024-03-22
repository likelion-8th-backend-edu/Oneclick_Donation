package com.example.OneclickDonation.post.repo;

import com.example.OneclickDonation.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
