package com.example.OneclickDonation.toss.repo;

import com.example.OneclickDonation.toss.entity.PostDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<PostDonation, Long> {
}
