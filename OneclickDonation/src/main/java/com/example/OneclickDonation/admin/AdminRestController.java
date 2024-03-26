package com.example.OneclickDonation.admin;

import com.example.OneclickDonation.admin.dto.UpgradeAdminDto;
import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService service;

    // 수락
    @PostMapping("/upgrades/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UpgradeAdminDto approve(
            @PathVariable("id")
            Long id
    ) {
        return service.acceptUpgrade(id);
    }

    // 거절
    @DeleteMapping("/upgrades/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UpgradeAdminDto disapprove(
            @PathVariable("id")
            Long id
    ) {
        return service.rejectUpgrade(id);
    }
}
