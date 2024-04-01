package com.example.OneclickDonation.toss.service;

import com.example.OneclickDonation.post.entity.Post;
import com.example.OneclickDonation.post.repo.PostRepository;
import com.example.OneclickDonation.toss.dto.PaymentConfirmDto;
import com.example.OneclickDonation.toss.dto.PostDonationDto;
import com.example.OneclickDonation.toss.entity.PostDonation;
import com.example.OneclickDonation.toss.repo.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationService {
    private final TossHttpService tossService;
    private final PostRepository postRepository;
    private final DonationRepository donationRepository;

    // 아이템을 결제하는 과정은 다 진행이 되고, 그거에 대한 데이터가 포장이 되서
    // 토스컨트롤러의 메서드로 데이터가 들어오고, 로그로 데이터가 뭔지 확인 후 서비스로 돌아온다.
    // 서비스 메서드로 돌아와서 결제 승인을 날리고, 그걸로 나서 돌아오는 객체가 어떤 모양일지를 로그로 확인 후 응답한다.
    // 결제는 받는 메서드
    public Object confirmPayment(PaymentConfirmDto dto) {
        // Http 요청이 보내진다.
        Object tossPaymentObj = tossService.confirmPayment(dto);
        log.info(tossPaymentObj.toString());
        // 1. 결제한 물품 정보를 응답 Body에서 찾는다 (orderName)
        String donationName = ((LinkedHashMap<String, Object>) tossPaymentObj)
                .get("donationName").toString();

        // 2. donationName에서 postId를 회수하고, 그에 해당하는 Post엔티티를 조회한다.
        Long postId = Long.parseLong(donationName.split("-")[0]);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        // 3. Post엔티티를 바탕으로 PostDonation을 만들자.
        return PostDonationDto.fromEntity(donationRepository.save(PostDonation.builder()
                .post(post)
                .tossPaymentKey(dto.getPaymentKey())
                .tossDonationId(dto.getOrderId())
                .status("DONE")
                .build()));

//        return tossPaymentObj;
    }
    // readAll
    public List<PostDonationDto> readAll() {
        return donationRepository.findAll().stream()
                .map(PostDonationDto::fromEntity)
                .toList();
    }

    // readOne
    public PostDonationDto readOne(Long id) {
        return donationRepository.findById(id)
                .map(PostDonationDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // readTossPayment
    // paymentKey로 결제 조회
    public Object readTossPayment(Long id) {
        // 1. id를 가지고 주문정보를 조회한다.
        PostDonation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 주문정보에 포함된 결제정보키(paymentKey)를 바탕으로
        // Toss에 요청을 보내 결제 정보를 받는다.
        // paymentkey는 itemOrderDto에 들어가 있다.
        Object response = tossService.getPayment(donation.getTossPaymentKey());
        log.info(response.toString());
        // 3. 해당 결제 정보를 반환한다.
        return response;
    }

    // donationId로 결제 조회
    public Object readTossDonation(Long id) {
        PostDonation order = donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Object response = tossService.getDonation(order.getTossDonationId());
        log.info(response.toString());
        return response;
    }

    // 결제 취소
//    @Transactional
//    public Object cancelPayment(Long id, PaymentCancelDto dto) {
//        // 1,. 취소한 주문을 찾는다.
//        ItemOrder order = orderRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        // 2. 주문 정보를 갱신한다.
//        // TODO 더티채킹에 관련되서 위의 코드가 진행되면 자동으로 진행이 되서, 취소까지 완료가 된다.
//        order.setStatus("CANCEL");
//        // 3. 취소후 결과를 응답한다.
//        return tossService.cancelPayment(order.getTossPaymentKey(), dto);
//    }


}
