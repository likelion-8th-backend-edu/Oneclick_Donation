package com.example.OneclickDonation.member.dto;

import com.example.OneclickDonation.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private String profile;
    private String nickname;
    private Integer age;
    private String phone;
    private Integer donationAmount;


    public static MemberDto fromEntity(Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .profile(entity.getProfile())
                .nickname(entity.getNickname())
                .age(entity.getAge())
                .phone(entity.getPhone())
                .donationAmount(entity.getDonationAmount())
                .build();
    }
}