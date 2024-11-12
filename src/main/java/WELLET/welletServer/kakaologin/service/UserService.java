package WELLET.welletServer.kakaologin.service;

import WELLET.welletServer.kakaologin.Repository.UserRepository;
import WELLET.welletServer.kakaologin.dto.KakaoUserInfoResponseDto;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.repository.MemberRepository;
import WELLET.welletServer.member.service.MemberService;
import lombok.RequiredArgsConstructor;

import WELLET.welletServer.kakaologin.domain.KakaoUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // 사용자 저장소 인터페이스
    private final MemberRepository memberRepository; // 사용자 저장소 인터페이스
    private final MemberService memberService;

    public boolean isNewUser(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId).isEmpty(); // 카카오 ID로 기존 사용자 확인
    }

    @Transactional
    public void registerNewUser(KakaoUserInfoResponseDto userInfo) {
//        KakaoUser user = KakaoUser.builder()
//                .kakaoId(userInfo.getId())
//                .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
//                .profileImage(userInfo.getKakaoAccount().getProfile().getProfileImageUrl())
//                .lastLoginTime(LocalDateTime.now())  // 최초 로그인 시간 설정
//                .build();

        System.out.println(userInfo);
        System.out.println(userInfo.getKakaoAccount().getProfile().getNickName());
        System.out.println(userInfo.getKakaoAccount().getProfile().getProfileImageUrl());

        Member member = Member.builder()
                .username(UUID.randomUUID())
                .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                .profileImage(userInfo.getKakaoAccount().getProfile().getProfileImageUrl())
                .lastLoginTime(LocalDateTime.now())
                .build();

        memberRepository.save(member);

//        memberService.saveMember(userInfo);
    }

    public void loginUser(KakaoUserInfoResponseDto userInfo) {
        Member user = memberRepository.findByKakaoId(userInfo.getId())
                .orElseThrow();
        user.updateLastLoginTime(LocalDateTime.now());
        memberRepository.save(user);
    }
}

