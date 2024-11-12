package WELLET.welletServer.kakaologin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class KakaoUser {

    @Id
    private Long kakaoId;

    @Column(name = "username", unique = true)
    private UUID username;

    private String nickname;
    private String profileImage;
    private LocalDateTime lastLoginTime;

    @Builder
    public KakaoUser(Long kakaoId, UUID username, String nickname, String profileImage, LocalDateTime lastLoginTime) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.lastLoginTime = lastLoginTime;
    }

    // 마지막 로그인 시간 업데이트용 메서드
    public void updateLastLoginTime(LocalDateTime Time) {
        this.lastLoginTime = Time;
    }
}

