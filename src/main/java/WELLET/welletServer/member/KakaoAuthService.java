package WELLET.welletServer.member;


import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {


    private final KakaoUserInfo kakaoUserInfo;
    private final UserRepository userRepository;

    @Transactional (readOnly = true)
    public Long isSignedUp(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
        User user = userRepository.findByKeyCode(userInfo.getId().toLong()).orElseThrow(() -> new ExecutionControl.UserException(ResponseCode.USER_NOT_FOUND));
        return user.getId();
    }
}
