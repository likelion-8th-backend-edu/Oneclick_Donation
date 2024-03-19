package com.example.OneclickDonation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;  // 아이디
    @Column(nullable = false)
    private String password;
    private String username;
    private String profile;
    private String phone;
    private Integer age;
    private String organization;  // 단체
    private Integer businessNumber;  // 사업자 번호
    private Integer donationAmount; // 개인 기부금
}
