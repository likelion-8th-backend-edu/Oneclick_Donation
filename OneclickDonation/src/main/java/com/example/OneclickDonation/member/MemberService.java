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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberUpgradeRepository upgradeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authFacade;
    private final FileHandlerUtils fileHandlerUtils;
    private final JwtTokenUtils jwtTokenUtils;

    public MemberService(
            MemberRepository memberRepository,
            MemberUpgradeRepository upgradeRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationFacade authFacade,
            FileHandlerUtils fileHandlerUtils,
            JwtTokenUtils jwtTokenUtils
    ) {
        this.memberRepository = memberRepository;
        this.upgradeRepository = upgradeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authFacade = authFacade;
        this.fileHandlerUtils = fileHandlerUtils;
        this.jwtTokenUtils = jwtTokenUtils;

        adminCreate(CustomUserDetails.builder()
                .username("admin@donation.com")
                .password(passwordEncoder.encode("password"))
                .nickname("관리자")
                .authorities(Role.ROLE_ADMIN.name())
                .build()

        );
        adminCreate(CustomUserDetails.builder()
                .username("alex@gmail.com")
                .password(passwordEncoder.encode("111"))
                .nickname("테스트 사용자")
                .authorities(Role.ROLE_INDIVIDUAL.name())
                .build()

        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .map(CustomUserDetails::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("email not found"));
    }

    // 관리자 생성
    public void adminCreate(UserDetails user) {
        CustomUserDetails userDetails = (CustomUserDetails) user;
        Member admin = Member.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .nickname(userDetails.getNickname())
                .authorities(userDetails.getRawAuthorities())
                .build();
        memberRepository.save(admin);
    }

    @Transactional
    public void register(RegisterDto dto) {
        if (!dto.getPassword().equals(dto.getRepeatPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

        if (memberRepository.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이미 존재한 이메일 입니다.");

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        MemberDto.fromEntity(memberRepository.save(Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .age(dto.getAge())
                .phone(dto.getPhone())
                .authorities(Role.ROLE_INDIVIDUAL.name())
                .build()));
    }

    @Transactional
    public JwtResponseDto signin(JwtRequestDto dto) {
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                member.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        String jwt = jwtTokenUtils.generateToken(CustomUserDetails.fromEntity(member));
        log.info("jwt 확인: {}", jwt);
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        log.info("response 확인: {}", response);
        return response;
    }

    // 개인-> 단체 사용자 전환 신청
    @Transactional
    public void upgradeRequest(MemberUpgradeDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Member member = memberRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자를 찾을 수 없습니다. 사용자 이름: " + loggedInUsername));
        // 전환 신청 엔티티 생성 및 설정
        MemberUpgrade upgrade = MemberUpgrade.builder()
                .member(member)
                .organization(dto.getOrganization())
                .businessNumber(dto.getBusinessNumber())
                .applicationReason(dto.getApplicationReason())
                .build();

        // 전환 신청 저장
        upgradeRepository.save(upgrade);
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

    // 마이페이지
    public MemberDto readOne(Long id) {
        return memberRepository.findById(id)
                .map(MemberDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
