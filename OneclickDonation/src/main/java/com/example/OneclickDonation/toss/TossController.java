package com.example.OneclickDonation.toss;

import com.example.OneclickDonation.toss.dto.PaymentConfirmDto;
import com.example.OneclickDonation.toss.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossController {
    private final DonationService donationService;

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto
    ) {
        log.info("received: {}", dto.toString());
        return donationService.confirmPayment(dto);
    }
}
