package com.example.OneclickDonation.config;


import com.example.OneclickDonation.jwt.JwtTokenFilter;
import com.example.OneclickDonation.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
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