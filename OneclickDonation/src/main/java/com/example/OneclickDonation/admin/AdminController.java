package com.example.OneclickDonation.admin;

import com.example.OneclickDonation.admin.dto.UpgradeAdminDto;
import com.example.OneclickDonation.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @GetMapping("/upgrades")
    public Page<UpgradeAdminDto> upgradeRequests(
            Pageable pageable
    ) {
        return service.listRequests(pageable);
    }

    @PutMapping("/upgrades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UpgradeAdminDto approve(
            @PathVariable("id")
            Long id
    ) {
        return service.approveUpgrade(id);
    }

    @DeleteMapping("upgrades/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UpgradeAdminDto disapprove(
            @PathVariable("id")
            Long id
    ) {
        return service.disapproveUpgrade(id);
    }
}
