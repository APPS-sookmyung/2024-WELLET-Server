package WELLET.welletServer.kakaologin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import org.springframework.http.HttpHeaders;

@Controller
@RequestMapping("/auth/kakao/callback")
public class KakaoLoginPageController {

    // .env에서 client_id와 redirect_uri 값을 불러오기
//    @Value("${KAKAO_CLIENT_ID}")
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/page")
    public ResponseEntity<Void> loginPage() {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
        URI uri = URI.create(location);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}

// SSL 인증서 필요
