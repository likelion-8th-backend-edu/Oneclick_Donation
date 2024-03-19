package com.example.OneclickDonation.repo;

import com.example.OneclickDonation.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
