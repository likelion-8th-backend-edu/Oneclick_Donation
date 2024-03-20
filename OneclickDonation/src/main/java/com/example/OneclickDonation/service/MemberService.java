package com.example.OneclickDonation.service;

import com.example.OneclickDonation.dto.CustomUserDetails;
import com.example.OneclickDonation.dto.MemberDto;
import com.example.OneclickDonation.dto.RegisterDto;
import com.example.OneclickDonation.entity.Member;
import com.example.OneclickDonation.repo.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(CustomUserDetails::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("email not found"));
    }

    @Transactional
    public MemberDto register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getRepeatPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (memberRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        return MemberDto.fromEntity(memberRepository.save(Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build()));
    }

    //로그인
    public MemberDto login(MemberDto memberDto) {
        Optional<Member> optionalMember = memberRepository.findByUsername(memberDto.getUsername());
        if (optionalMember.isPresent()) {
            Member memberEntity = optionalMember.get();
            if (passwordEncoder.matches(memberDto.getPassword(), memberEntity.getPassword())) {
                return memberDto;
            }
        }
        return null;
    }



    // 개인-> 단체 사용자 전환 신청
}
