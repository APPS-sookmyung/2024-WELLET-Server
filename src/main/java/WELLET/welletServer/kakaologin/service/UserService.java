package WELLET.welletServer.kakaologin.service;

import WELLET.welletServer.kakaologin.dto.KakaoUserInfoResponseDto;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository; // 사용자 저장소 인터페이스

    public boolean isNewUser(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId).isEmpty(); // 카카오 ID로 기존 사용자 확인
    }

    public void loginUser(KakaoUserInfoResponseDto userInfo) {
        Member user = memberRepository.findByKakaoId(userInfo.getId())
                .orElseThrow();
        user.updateLastLoginTime(LocalDateTime.now());
        memberRepository.save(user);
    }
}

