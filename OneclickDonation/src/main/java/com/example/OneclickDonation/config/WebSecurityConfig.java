package com.example.OneclickDonation.config;

import com.example.OneclickDonation.jwt.JwtTokenFilter;
import com.example.OneclickDonation.jwt.JwtTokenUtils;
//import com.example.OneclickDonation.oauth.OAuth2SuccessHandler;
import com.example.OneclickDonation.oauth.OAuth2SuccessHandler;
import com.example.OneclickDonation.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService manager;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/static/**",
                                "/donation",
                                "/donation/signup",
                                "/donation/signin",
                                "/post/create",
                                "/post/{postId}",
                                "/post/{postId}/news",
                                "/post/{postId}/edit",
                                "/post/{postId}/delete",
                                "/post/{postId}/comment",
                                "/post/{postId}/comment/{commentId}/delete",
                                "/post/{postId}/support-amount-target-amount",
                                "/donation/upgrade-request",
                                "/admin",
                                "/admin/upgrades",
                                "/admin/upgrades/{id}",
                                "/admin/upgrades/{id}/accept",
                                "/admin/upgrades/{id}/reject"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );
        //세션 설정
        http
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/donation")
                        .userInfoEndpoint(userinfo -> userinfo
                                .userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        new JwtTokenFilter(jwtTokenUtils, manager),
                        AuthorizationFilter.class
                )
        ;
        return http.build();
    }
}