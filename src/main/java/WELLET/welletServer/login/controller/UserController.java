package WELLET.welletServer.login.controller;

import WELLET.welletServer.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    @GetMapping("/kakao/callback")
    public CustomResponseEntity<UserResponse.Login> loginByKakao(@RequestParam String authorizationCode) {
        //서비스 레이어를 통해 카카오 로그인 처리
        UserResponse.Login loginResponse = userService.loginByKakao(authorizationCode);
        return CustomResponseEntity.success(loginResponse);
    }
}
