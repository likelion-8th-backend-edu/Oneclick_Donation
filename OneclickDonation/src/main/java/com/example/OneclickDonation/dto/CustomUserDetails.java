package com.example.OneclickDonation.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String username;  // 밑에 필수 메서드 때문에 이름을 username으로 사용
    private String profile;
    private String phone;
    private String age;
    private String organization;
    private Integer businessNumber;
    private Integer donationAmount;
    private String authorities;



    public String getRawAuthorities() {
        return this.authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities  = new ArrayList<>();
        if (this.authorities != null) {
            String[] rawAuthorities = authorities.split(",");
            for (String rawAuthority : rawAuthorities) {
                // SimpleGrantedAuthority:Spring Security 내부에서 보통 String 형식의 권한을 표현하는 방식
                grantedAuthorities.add(new SimpleGrantedAuthority(rawAuthority));
            }
        }
        return grantedAuthorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
