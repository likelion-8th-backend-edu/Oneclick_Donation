package com.example.OneclickDonation.admin.controller;

import com.example.OneclickDonation.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
    public void accept(
            @PathVariable("id")
            Long id
    ) {
        service.acceptUpgrade(id);
    }


    // 거절
    @DeleteMapping("/upgrades/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reject(
            @PathVariable("id")
            Long id,
            @RequestBody
            String rejectionReason
    ) {
        service.rejectUpgrade(id, rejectionReason);
    }
}
