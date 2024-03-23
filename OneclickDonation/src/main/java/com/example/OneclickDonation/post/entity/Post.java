package com.example.OneclickDonation.post.entity;

import com.example.OneclickDonation.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @Setter
    private String startDate; // 모금 시작 날짜
    @Setter
    private String endDate; // 모금 종료 날짜
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.ING;
    // 상태(진행중, 종료)
    // if enddate + 1 ==> 상태 = 종료
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private final List<Comment> comments = new ArrayList<>();

}
