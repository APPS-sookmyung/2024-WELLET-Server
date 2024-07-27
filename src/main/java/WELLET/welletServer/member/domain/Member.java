package WELLET.welletServer.member.domain;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.category.domain.CategoryCard;
import WELLET.welletServer.common.BaseTimeEntity;
import WELLET.welletServer.member.dto.MemberUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Category> categorys = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Card> Cards = new ArrayList<>();

//    private String profile_image;

    @Builder
    public Member(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
//        this.profile_image = profile_image;
    }

    public void updateMember(MemberUpdateDto dto) {
        this.username = dto.getUsername();
        this.nickname = dto.getNickname();
    }
}
