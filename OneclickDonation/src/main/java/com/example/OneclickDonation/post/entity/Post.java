package com.example.OneclickDonation.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String title; // 모금 제안 제목
    @Setter
    private String postImage; // imageUrl을 postImage로 변경
    @Setter
    private String description;
    private Integer supportAmount; // 모금 현황
    @Setter
    private Integer targetAmount; // 목표 모금 금액
    private String startDate; // 모금 시작 날짜
    private String endDate; // 모금 종료 날짜
    // 상태(진행중, 종료)
    // if enddate + 1 ==> 상태 = 종료

}
