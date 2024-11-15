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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final JwtService jwtService;
    private final UserService userService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
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
            jwtCookie.setHttpOnly(true);  // JavaScript로 쿠키에 접근 불가
//            jwtCookie.setSecure(true);    // HTTPS에서만 전송
            jwtCookie.setMaxAge(60 * 60 * 24);  // 쿠키 유효 시간 설정
            jwtCookie.setPath("/");  // 쿠키를 모든 경로에 적용
            response.addCookie(jwtCookie);

            // 6. 성공적으로 로그인 완료 시 OK 응답
            return ResponseEntity.ok("로그인 성공");

        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생", e);
            return new ResponseEntity<>("로그인 처리 중 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}





