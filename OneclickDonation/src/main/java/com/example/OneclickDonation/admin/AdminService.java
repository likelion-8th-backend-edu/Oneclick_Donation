package com.example.OneclickDonation.admin;

import com.example.OneclickDonation.Enum.Role;
import com.example.OneclickDonation.admin.dto.UpgradeAdminDto;
import com.example.OneclickDonation.member.dto.MemberUpgradeDto;
import com.example.OneclickDonation.member.entity.MemberUpgrade;
import com.example.OneclickDonation.member.repo.MemberRepository;
import com.example.OneclickDonation.member.repo.MemberUpgradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final MemberUpgradeRepository upgradeRepository;


    // 전환 신청 보기
    public Page<UpgradeAdminDto> listRequests(Pageable pageable) {
        return upgradeRepository.findAll(pageable)
                .map(UpgradeAdminDto::fromEntity);
    }

    // 단체 사용자 신청 수락
    @Transactional
    public UpgradeAdminDto acceptUpgrade(Long id) {
        MemberUpgrade upgrade = upgradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        upgrade.setApproved(true);
        upgrade.getUpgradeMem().setAuthorities(Role.ROLE_GROUP.name());
        // TODO 캠페인을 쓸 수 있도록 하기

        return UpgradeAdminDto.fromEntity(upgrade);
    }

    // 단체 사용자 신청 거절
    @Transactional
    public UpgradeAdminDto rejectUpgrade(Long id) {
        MemberUpgrade upgrade = upgradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        upgrade.setApproved(false);
        return UpgradeAdminDto.fromEntity(upgrade);
    }

}
