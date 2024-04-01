package com.example.OneclickDonation.toss.service;

import com.example.OneclickDonation.toss.dto.PaymentConfirmDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

// 메서드에 http요청을 보내는 것이다.
// 해당 메서드 호출 시 HTTP 요청이 보내진다.
// HTTP Interface는 혼자 사용할 수 없어 구현체를 만들어야한다. 프록시
@HttpExchange("/payments")
public interface TossHttpService {
    @PostExchange("/confirm")
    Object confirmPayment(@RequestBody PaymentConfirmDto dto);

    @GetExchange("/{paymentsKey}")
    Object getPayment(
            @PathVariable("paymentsKey") String paymentKey
    );

    @GetExchange("/donation/{donationId}")
    Object getDonation(@PathVariable("donationId") String donationId);

//    @PostExchange("{paymentKey}/cancel")
//    Object cancelPayment(
//            @PathVariable("paymentKey") String tossPaymentKey,
//            @RequestBody PaymentCancelDto dto
//    );
}