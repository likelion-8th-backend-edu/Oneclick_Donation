package com.example.OneclickDonation.config;

import com.example.OneclickDonation.toss.service.TossHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Base64;

@Configuration
public class RestClientConfig {
    @Value("${toss.secret}")
    private String tossSecret;

    @Bean
    // HTTP Client 만들기
    public RestClient tossClient() {
        // Basic Authentication 헤더 설정
        String basicAuth = Base64.getEncoder().encodeToString((tossSecret + ":").getBytes());
        return RestClient.builder()
                .baseUrl("https://api.tosspayments.com/v1")
                .defaultHeader("Authorization", String.format("Basic %s", basicAuth)) // 프론트에서는 잘 사용 안함.
                .build();
    }

    @Bean
    //  Toss API와 상호 작용하기 위한 TossHttpService 인터페이스의 구현을 동적으로 생성하고,
    //  이를 통해 서비스 레이어에서 Toss API를 호출하기 위한 메서드들이 구현된 객체를 주입받아 사용할 수 있게 됩니다.
    public TossHttpService httpService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(tossClient()))
                .build()
                .createClient(TossHttpService.class);
    }
}
