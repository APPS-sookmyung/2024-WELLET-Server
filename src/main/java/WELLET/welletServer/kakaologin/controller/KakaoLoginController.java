package WELLET.welletServer.kakaologin.controller;

import WELLET.welletServer.kakaologin.dto.KakaoUserInfoResponseDto;
import WELLET.welletServer.kakaologin.jwt.JwtService;
import WELLET.welletServer.kakaologin.service.KakaoService;
import WELLET.welletServer.kakaologin.service.UserService;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import WELLET.welletServer.member.repository.MemberRepository;
import WELLET.welletServer.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final JwtService jwtService;
    private final UserService userService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Value("${kakao.login_uri}")
    private String loginUrl;

    @Value("${cors.allowed.origin}")
    private String frontendUrl;

    // application.yml에서 client_id와 redirect_uri 값을 불러오기
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Value("${cors.allowed.domain}")
    private String domain;

    @GetMapping("/login")
    public String login(HttpServletResponse response) throws IOException {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
        URI uri = URI.create(location);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
//        response.sendRedirect(location);

        return loginUrl;
    }


    @GetMapping("/auth/kakao/callback")
    public String callback(@RequestParam("code") String code, HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            // 1. 카카오에서 accessToken 받아오기
            String accessToken = kakaoService.getAccessTokenFromKakao(code);
            // 2. accessToken으로 사용자 정보 받아오기
            KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

            // 3. 회원가입 또는 로그인 처리
//            KakaoUser user;
            Member member;
            if (userService.isNewUser(userInfo.getId())) {
                member = memberService.saveMember(userInfo);
            } else {
                // 기존 사용자 검색
                Member existingUser = memberRepository.findByKakaoId(userInfo.getId())
                        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
                existingUser.updateLastLoginTime(LocalDateTime.now());
                member = existingUser;  // 기존 사용자로 설정
            }

            // 4. JWT 생성
            String jwtToken = jwtService.generateToken(member);  // 생성된 또는 업데이트된 사용자로 JWT 생성

            // 5. 쿠키에 JWT 저장
            Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);  // HTTPS에서만 전송 X
            jwtCookie.setMaxAge(60 * 60 * 24);  // 쿠키 유효 시간 설정
            jwtCookie.setPath("/");
            jwtCookie.setDomain(domain);
//            response.addCookie(jwtCookie);

//            response.addHeader("Set-Cookie", "jwtToken=" + jwtToken + "; Path=/; HttpOnly; Secure; Max-Age=" + (60 * 60 * 24) + "; SameSite=None");

//            response.setHeader("Set-Cookie","token=" + jwtToken +. ;Path=/; Domain=localhost; HttpOnly; Max-Age=604800; SameSite=None; Secure;");

//            response.setHeader(
//                    "Set-Cookie",
//                    "token=" + jwtToken
//                            + "; Path=/"
////                            + "; Domain=wellet.netlify.app"
////                            + "; HttpOnly"
//                            + "; Max-Age=604800"
//                            + "; SameSite=None"
////                            + "; Secure"
//            );


//            jwtCookie.setDomain(frontendUrl);
//            response.addCookie(jwtCookie);

//            ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwtToken)
//                    .httpOnly(true)
////                    .secure(true)
//                    .path("/")
//                    .sameSite("None")
//                    .maxAge(60 * 60 * 24)
//                    .build();
//            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());


            // 리다이렉트 URL 설정
            String redirectUrl = getFrontendUrl(request) + "/home";
            response.addCookie(jwtCookie);
            response.sendRedirect(redirectUrl);
            return "로그인 성공";

        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생", e);
            return "로그인 처리 중 오류" + HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String getFrontendUrl(HttpServletRequest request) {
        String redirectUrl;

        String origin = request.getHeader("referer");  // 요청의 출처를 가져옴

        if (origin != null) {
            // 요청의 출처가 localhost일 경우
            if (origin.contains("localhost:8000")) {
                redirectUrl = "http://localhost:8000";
            }
            // 요청의 출처가 배포 url인 경우
            else if (origin.contains(frontendUrl)) {
                redirectUrl = frontendUrl;
            } else {
                redirectUrl = frontendUrl;
            }
        } else {
            redirectUrl = frontendUrl;
        }

        return redirectUrl;
    }
}





