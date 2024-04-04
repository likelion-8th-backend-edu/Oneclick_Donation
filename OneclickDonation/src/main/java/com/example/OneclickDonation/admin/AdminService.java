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
        upgrade.getMember().setAuthorities(Role.ROLE_GROUP.name());
        upgrade.getMember().setOrganization(upgrade.getOrganization());
        return UpgradeAdminDto.fromEntity(upgrade);
    }

    // 단체 사용자 신청 거절
    @Transactional
    public void rejectUpgrade(Long id, String rejectionReason) {
        MemberUpgrade upgrade = upgradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        upgrade.setApproved(false);
        upgrade.setRejectReason(rejectionReason);
        upgradeRepository.deleteById(id);
    }

}
