package com.example.OneclickDonation.post.entity;

import com.example.OneclickDonation.Enum.Status;
import com.example.OneclickDonation.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private String organization; // 조직

    @Setter
    @Column(name = "post_image")
    private String postImage; // imageUrl을 postImage로 변경

    @Setter
    private String description;

    @Setter
    @Builder.Default
    private Integer supportAmount = 0; // 기본값 설정

    @Setter
    private Integer targetAmount; // 목표 모금 금액

    @Setter
    private String startDate; // 모금 시작 날짜

    @Setter
    private String endDate; // 모금 종료 날짜

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.ING; // 상태(진행중, 종료)

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private final List<Comment> comments = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private News news;




    public void updateStatus() {
        if (supportAmount >= targetAmount) {
            this.status = Status.END;
        } else {
            LocalDate today = LocalDate.now();
            LocalDate startDate = LocalDate.parse(this.startDate);
            LocalDate endDate = LocalDate.parse(this.endDate);

            if (today.isAfter(endDate) || today.isEqual(endDate) || startDate.isEqual(endDate) || startDate.isAfter(endDate)) {
                this.status = Status.END;
            }
        }
    }

    public boolean isEng() {
        return this.status == Status.END;
    }

    @PreUpdate
    public void beforeUpdate() {
        updateStatus(); // 상태 업데이트 메서드 호출
    }
}

