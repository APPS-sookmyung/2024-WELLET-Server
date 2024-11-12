package WELLET.welletServer.member.domain;

import WELLET.welletServer.common.BaseTimeEntity;
import WELLET.welletServer.member.dto.MemberUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private UUID username;

    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password")
    private String password;

    private String profileImage;
    private LocalDateTime lastLoginTime;


    @Builder
    public Member(Long kakaoId, UUID username, String nickname, String password, String profileImage, LocalDateTime lastLoginTime) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.profileImage = profileImage;
        this.lastLoginTime = lastLoginTime;
    }

    public void updateLastLoginTime(LocalDateTime Time) {
        this.lastLoginTime = Time;
    }


    public void updateMember(MemberUpdateDto dto) {
        this.nickname = dto.getNickname();
    }
}
