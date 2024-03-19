package com.example.OneclickDonation.repo;

import com.example.OneclickDonation.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
