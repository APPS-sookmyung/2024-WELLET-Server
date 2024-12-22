package WELLET.welletServer.kakaologin.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpHeaders;

@Controller
@RequestMapping("/auth/kakao/callback")
public class KakaoLoginPageController {

    // application.yml에서 client_id와 redirect_uri 값을 불러오기
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

//    @GetMapping("/login")
//    public void login(HttpServletResponse response) throws IOException {
//        String redirectUri = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=856122255feeea21d537c0225f6c658a&redirect_uri=http://localhost:8080/auth/kakao/callback";
//        response.sendRedirect(redirectUri);
//
////        String newRedirectUri = "https://wellet.netlify.app";
////        response.sendRedirect(newRedirectUri);
//    }

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
