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
import org.springframework.web.bind.annotation.*;

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
    public String homePage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        // 페이지네이션에 사용할 페이지 크기
        int pageSize = 12;
        Page<PostDto> postPage = postService.readPage(PageRequest.of(page, pageSize));
        model.addAttribute("page", postPage);
        return "admin/home";
    }

    @GetMapping("/admin/end")
    public String endPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        // 페이지네이션에 사용할 페이지 크기
        int pageSize = 12;
        Page<PostDto> postPage = postService.readEndPost(PageRequest.of(page, pageSize));
        model.addAttribute("page", postPage);
        return "admin/endPost";
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
