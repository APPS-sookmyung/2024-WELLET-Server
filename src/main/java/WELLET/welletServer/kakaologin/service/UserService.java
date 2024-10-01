package WELLET.welletServer.kakaologin.service;

import WELLET.welletServer.kakaologin.Repository.UserRepository;
import WELLET.welletServer.kakaologin.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;

import WELLET.welletServer.kakaologin.domain.KakaoUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // 사용자 저장소 인터페이스

    public boolean isNewUser(Long kakaoId) {
        return !userRepository.existsByKakaoId(kakaoId); // 카카오 ID로 기존 사용자 확인
    }

    public void registerNewUser(KakaoUserInfoResponseDto userInfo) {
        KakaoUser user = KakaoUser.builder()
                .kakaoId(userInfo.getId())
                .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                .profileImage(userInfo.getKakaoAccount().getProfile().getProfileImageUrl())
                .lastLoginTime(LocalDateTime.now())  // 최초 로그인 시간 설정
                .build();
        userRepository.save(user); // 사용자 정보 저장
    }

    public void loginUser(KakaoUserInfoResponseDto userInfo) {
        KakaoUser user = userRepository.findByKakaoId(userInfo.getId());
        user.updateLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
    }
}

