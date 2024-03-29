package com.example.OneclickDonation.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
//@NoArgsConstructor
//@AllArgsConstructor// -- 이거 때문에 객체가 구성이 안됨.
public class RegisterDto {
    private String username;
    private String password;
    private String repeatPassword;
    private String nickname;
    private String phone;
    private Integer age;
}
