package com.example.OneclickDonation.toss.dto;

import com.example.OneclickDonation.toss.entity.PostDonation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDonationDto {
    private Long id;
    private Long postId;
    private String postTitle;
    private String tossPaymentKey;
    private String tossDonationId;
    private String status;

    public static PostDonationDto fromEntity(PostDonation entity) {
        return PostDonationDto.builder()
                .id(entity.getId())
                .postId(entity.getPost().getId())
                .postTitle(entity.getPost().getTitle())
                .tossPaymentKey(entity.getTossPaymentKey())
                .tossDonationId(entity.getTossDonationId())
                .status(entity.getStatus())
                .build();
    }
}
