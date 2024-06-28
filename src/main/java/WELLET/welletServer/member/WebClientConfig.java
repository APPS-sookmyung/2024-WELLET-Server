package WELLET.welletServer.common.Member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorResourceFactory;

@Configuration //빈을 생성하기 위해
public class WebClientConfig {

    @Bean
    public ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false); //공유나 글로벌 리소스 방지 (독립적으로 관리하겠다는 의미)
        return  factory;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://localhost:8080/oauth2/kakao/callback")
                .build();
    }
}
