package WELLET.welletServer.login.service;

import WELLET.welletServer.login.jwt.JwtService;
import WELLET.welletServer.login.repository.KakaoTokenResponse;
import WELLET.welletServer.login.repository.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final kakaoProperties kakaoProperties;
    private RestTemplate restTemplate = new RestTemplate();

    public UserResponse.Login loginByKakao(String authorizationCode) {
        // 1. 카카오 인가코드로 엑세스 토큰 받기
        KakaoTokenResponse kakaoTokenResponse = getKakaoToken(authorizationCode);
        // 2. 엑세스 토큰으로 카카오에서 유저 정보 조회
        KakaoUserResponse kakaoUserResponse = getKakaoUserInfo(kakaoTokenResponse);
        // 3. 로그인 처리
        Users user = saveOrUpdate(kakaoUserResponse);
        // 4. 엑세스 토큰 발급
        String accessToken = jwtService.createAccessToken(user.getEmail());
        // 5. 리프레쉬 토큰 발급
        String refreshToken = jwtService.createRefreshToken(user.getEmail());

        return UserLoginResponse.reponse(user, accessToken, refreshToken);
    }

    // 1
    private KakaoTokenResponse getKakaoToken(String authorizationCode) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", kakaoProperties.getGrantType());
        params.add("client_id", kakaoProperties.getClientId());
        params.add("redirect_uri", kakaoProperties.getRedirectUri());
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.postForObject(
                kakaoProperties.getTokenUri(), request, kakaoTokenResponse.class
        );
    }

    // 2
    private KakaoUserResponse getKakaoUserInfo(kakaoTokenResponse kakaoTokenResponse) {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(kakaoTokenResponse.getAccessToken());

        return restTemplate.exchange(
                kakaoProperties.getUserInfoUri(),HttpMethod.GET, entity, KakaoUserResponse.class
        ).getBody();
    }

    // 사용자 저장 및 업데이트ㄱ
    private Users saveOrUpdate(KakaoTokenResponse kakaoTokenResponse) {
        String email = kakaoUserResponse.getKakaoAccount().getEmail();
        String name = kakaoUserResponse.getProperties().getNickname();

        Users user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(toUserEntity(kakaoUserResponse));
        return userRepository.save(user);
    }

    // 카카오 사용자 정보 User 엔티티 변환
    private Users toUserEntity(KakaoUserResponse kakaoUserResponse) {
        return Users.builder()
                .email(kakaoUserResponse.getKakaoAccount().getEmail())
                .name(kakaoUserResponse.getProperties().getNickname())
                .build();
    }
}

