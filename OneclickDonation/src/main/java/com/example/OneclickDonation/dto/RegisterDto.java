package com.example.OneclickDonation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
//@NoArgsConstructor
//@AllArgsConstructor -- 이거 때문에 객체가 구성이 안됨.
public class RegisterDto {
    private String username;
    private String password;
    private String repeatPassword;
}
