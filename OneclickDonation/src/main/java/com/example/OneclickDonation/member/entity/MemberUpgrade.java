package com.example.OneclickDonation.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpgrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String organization;  // 단체
    @Column(nullable = false)
    private Integer businessNumber;  // 사업자 번호
    @Column(nullable = false)
    private String applicationReason; // 신청 사유
    @Setter
    private String rejectReason; // 거절 사유(관리자)
    @Setter
    private Boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 전환 신청을 한 사용자

}

