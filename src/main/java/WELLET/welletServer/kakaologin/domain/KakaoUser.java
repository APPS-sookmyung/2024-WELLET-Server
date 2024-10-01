package WELLET.welletServer.kakaologin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class KakaoUser {

    @Id
    private Long kakaoId;
    private String nickname;
    private String profileImage;
    private LocalDateTime lastLoginTime;

    @Builder
    public KakaoUser(Long kakaoId, String nickname, String profileImage, LocalDateTime lastLoginTime) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.lastLoginTime = lastLoginTime;
    }

    // 마지막 로그인 시간 업데이트용 메서드
    public void updateLastLoginTime(LocalDateTime Time) {
        this.lastLoginTime = Time;
    }
}

