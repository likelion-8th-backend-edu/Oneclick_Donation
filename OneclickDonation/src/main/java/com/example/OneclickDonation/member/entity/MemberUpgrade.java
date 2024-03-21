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
    @ManyToOne(fetch = FetchType.LAZY)
    private Member upgradeMem;
    private String organization;  // 단체
    private Integer businessNumber;  // 사업자 번호
    @Setter
    private Boolean approved;
}
