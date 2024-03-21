package com.example.OneclickDonation.config;


import com.example.OneclickDonation.jwt.JwtTokenFilter;
import com.example.OneclickDonation.jwt.JwtTokenUtils;
import com.example.OneclickDonation.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService manager;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

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
                                "/post/{postId}/edit",
                                "/post/{postId}/delete",
                                "/upgrade-request"
                        )
                        .permitAll()


                        .anyRequest()
                        .authenticated()

                );


        //커스텀 로그인 필터 추가
        AuthenticationManager authManager = authenticationManager(authenticationConfiguration);
        http.addFilterAt(new LoginFilter(authManager), UsernamePasswordAuthenticationFilter.class);

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