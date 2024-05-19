package WELLET.welletServer.common.Member;

import WELLET.welletServer.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    private String profile_image;

    @Builder
    public Member(Long id, String username, String nickname, String password, String profile_image) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.profile_image = profile_image;
    }
}