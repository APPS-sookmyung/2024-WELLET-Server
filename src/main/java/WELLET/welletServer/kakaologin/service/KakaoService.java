package WELLET.welletServer.kakaologin.service;

import WELLET.welletServer.kakaologin.dto.KakaoTokenResponseDto;
import WELLET.welletServer.kakaologin.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {
    @Value("${kakao.client_id}") // 프로퍼티 파일에서 클라이언트 ID를 가져옵니다.
    private String clientId;

    @Value("${kakao.redirect_uri}") // 프로퍼티 파일에서 리다이렉트 URI를 가져옵니다.
    private String redirectUri;

    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    // 카카오 API에서 액세스 토큰 받아오기
    public String getAccessTokenFromKakao(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                // 에러 핸들링
                .onStatus(httpStatus -> httpStatus.is4xxClientError(), clientResponse ->
                        Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(httpStatus -> httpStatus.is5xxServerError(), clientResponse ->
                        Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        log.info(" [Kakao Service] Access Token: {}", kakaoTokenResponseDto.getAccessToken());
        log.info(" [Kakao Service] Refresh Token: {}", kakaoTokenResponseDto.getRefreshToken());

        return kakaoTokenResponseDto.getAccessToken();
    }

    // 카카오 API에서 사용자 정보 받아오기
    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 추가
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                // 에러 핸들링
                .onStatus(httpStatus -> httpStatus.is4xxClientError(), clientResponse ->
                        Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(httpStatus -> httpStatus.is5xxServerError(), clientResponse ->
                        Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info("[Kakao Service] Auth ID: {}", userInfo.getId());
        log.info("[Kakao Service] NickName: {}", userInfo.getKakaoAccount().getProfile().getNickName());
        log.info("[Kakao Service] Profile Image URL: {}", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        return userInfo;
    }
}




