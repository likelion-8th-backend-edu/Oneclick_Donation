package com.example.OneclickDonation.admin.controller;

import com.example.OneclickDonation.admin.AdminService;
import com.example.OneclickDonation.admin.dto.UpgradeAdminDto;
import com.example.OneclickDonation.post.dto.PostDto;
import com.example.OneclickDonation.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminController {
    private final AdminService service;
    private final PostService postService;

    @Autowired
    public AdminController(AdminService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }

    @GetMapping("/admin")
    public String adminPage(
            Pageable pageable,
            Model model
    ) {
        Page<PostDto> page = postService.readPage(PageRequest.of(0, 10));
        model.addAttribute("page", page);
        return "admin/home";
    }

    @GetMapping("/admin/upgrades")
    public String upgradeRequests(
            Pageable pageable,
            Model model
    ) {
        Page<UpgradeAdminDto> upgradeList = service.listRequests(pageable);
        model.addAttribute("upgradeList", upgradeList);
        return "admin/upgrade";
    }
}
