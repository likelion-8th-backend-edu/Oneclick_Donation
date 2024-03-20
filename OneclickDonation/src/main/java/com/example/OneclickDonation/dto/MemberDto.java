package com.example.OneclickDonation.dto;

import com.example.OneclickDonation.entity.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private String profile;
    private String phone;
    private Integer age;
    private String organization;
    private Integer businessNumber;
    private Integer donationAmount;


    public static MemberDto fromEntity(Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .profile(entity.getProfile())
                .phone(entity.getPhone())
                .age(entity.getAge())
                .organization(entity.getOrganization())
                .businessNumber(entity.getBusinessNumber())
                .donationAmount(entity.getDonationAmount())
                .build();
    }
}
