package com.example.OneclickDonation.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageDto {
    private String nickname; //이름
    private String username; //이메일
    private Integer age;
    private Integer phone;
    private Integer donationAmount; //개인 총 기부금액
}
