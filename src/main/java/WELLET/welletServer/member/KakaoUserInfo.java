package WELLET.welletServer.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoUserInfo {

    private final webClient webClient;
    private static final String USER_INFO_URI= "https://localhost:8080/oauth2/kakao/callback";

    public KakaoUserInfoResponse getUserInfo(String token) {
        Flux<KakaoUserInfoResponse> response = webClient.get()
                .URI(USER_INFO_URI)
                .header("Authorization", "Bearer" + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);
        return response.blockFirst();
    }
}
