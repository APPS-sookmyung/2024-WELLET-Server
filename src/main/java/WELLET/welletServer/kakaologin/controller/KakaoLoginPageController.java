package WELLET.welletServer.kakaologin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/kakao/callback")
public class KakaoLoginPageController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/page")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=\"+client_id+\"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);

        return "login";
    }
}

//https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=856122255feeea21d537c0225f6c658a&redirect_uri=https://localhost:8080/auth/kakao/callback 접속 가능

// https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=856122255feeea21d537c0225f6c658a&redirect_uri=https://0dd7-59-15-81-194.ngrok-free.app/auth/kakao/callback 접속 가능