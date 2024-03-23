package com.example.OneclickDonation.admin;

import com.example.OneclickDonation.admin.dto.UpgradeAdminDto;
import com.example.OneclickDonation.member.dto.MemberDto;
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
public class AdminController {
    private final AdminService service;
    private final PostService postService;

    @GetMapping
    public String adminPage(
            Pageable pageable,
            Model model
    ) {
        Page<PostDto> page = postService.readPage(PageRequest.of(0, 10));
        model.addAttribute("page", page);
        return "admin/home";
    }

    @GetMapping("/upgrades")
    public String upgradeRequests(
            Pageable pageable,
            Model model
    ) {
        Page<UpgradeAdminDto> upgradeList = service.listRequests(pageable);
        model.addAttribute("upgradeList", upgradeList);
        return "admin/upgrade";
    }

    // 수락
    @PostMapping("/acceptUpgrade/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UpgradeAdminDto approve(
            @PathVariable("id")
            Long id
    ) {
        return service.acceptUpgrade(id);
    }

    // 거절
    @DeleteMapping("/rejectUpgrade/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UpgradeAdminDto disapprove(
            @PathVariable("id")
            Long id
    ) {
        return service.rejectUpgrade(id);
    }
}
