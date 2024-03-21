package com.example.OneclickDonation.member;

import com.example.OneclickDonation.AuthenticationFacade;
import com.example.OneclickDonation.Enum.Role;
import com.example.OneclickDonation.FileHandlerUtils;
import com.example.OneclickDonation.jwt.JwtRequestDto;
import com.example.OneclickDonation.jwt.JwtResponseDto;
import com.example.OneclickDonation.jwt.JwtTokenUtils;
import com.example.OneclickDonation.member.dto.CustomUserDetails;
import com.example.OneclickDonation.member.dto.MemberDto;
import com.example.OneclickDonation.member.dto.MemberUpgradeDto;
import com.example.OneclickDonation.member.dto.RegisterDto;
import com.example.OneclickDonation.member.entity.Member;
import com.example.OneclickDonation.member.entity.MemberUpgrade;
import com.example.OneclickDonation.member.repo.MemberRepository;
import com.example.OneclickDonation.member.repo.MemberUpgradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberUpgradeRepository upgradeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authFacade;
    private final FileHandlerUtils fileHandlerUtils;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(CustomUserDetails::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("email not found"));
    }

    @Transactional
    public MemberDto register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getRepeatPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

        if (memberRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        return MemberDto.fromEntity(memberRepository.save(Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .age(dto.getAge())
                .phone(dto.getPhone())
                .authorities(Role.ROLE_INDIVIDUAL.name())
                .build()));
    }

    //로그인
    @Transactional
    public MemberDto login(MemberDto memberDto) throws UsernameNotFoundException {
        return memberRepository.findByUsername(memberDto.getUsername())
                .filter(member -> passwordEncoder.matches(memberDto.getPassword(), member.getPassword()))
                .map(member -> memberDto)
                .orElseThrow(() -> new UsernameNotFoundException("로그인 정보가 올바르지 않습니다.") {
                });
    }

    // 여기서 생각해봐야될 것! -> ddl-auto: upddate로 하면 jwt가 발급이 안됨.
    // ddl-auto: create로 해도 header에 토큰을 어떻게 넣어야되는지..?
    /*public JwtResponseDto signin(JwtRequestDto dto) {
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                member.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        log.info("로그인 이메일 확인: {}", dto.getUsername());
        log.info("로그인 비번 확인: {}", dto.getPassword());
        String jwt = jwtTokenUtils.generateToken(CustomUserDetails.fromEntity(member));
        log.info("jwt 확인: {}", jwt);
        JwtResponseDto response = new JwtResponseDto();
        log.info("response 확인: {}", response);  // 토큰이 안들어온다..
        response.setToken(jwt);
        return response;
    }*/

    // 개인-> 단체 사용자 전환 요청
    public void upgradeRequest(MemberUpgradeDto dto) {
        log.info("실행 확인");
        Member member = authFacade.extractUser();
        log.info("멤버 확인: {}", member);
        upgradeRepository.save(MemberUpgrade.builder()
                .upgradeMem(member)
                .organization(dto.getOrganization())
                .businessNumber(dto.getBusinessNumber())
                .build());
    }









    // 프로필
    public MemberDto profileImg(MultipartFile file) {
        Member member = authFacade.extractUser();
        String requestPath = fileHandlerUtils.saveFile(
                String.format("users/%d/", member.getId()),
                "profile",
                file
        );

        member.setProfile(requestPath);
        return MemberDto.fromEntity(memberRepository.save(member));
    }
}
