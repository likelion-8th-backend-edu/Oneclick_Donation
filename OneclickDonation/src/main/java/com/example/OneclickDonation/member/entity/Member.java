package com.example.OneclickDonation.member.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String username;  // 아이디(이메일)
    @Column(nullable = false)
    private String password;
    @Setter
    private String profile;
    private String nickname;
    private Integer age;
    private String phone;
    private  String organization;
    private Integer donationAmount; // 개인 기부금
    @Setter
    private String authorities; // 사용자 역할 권한
}
