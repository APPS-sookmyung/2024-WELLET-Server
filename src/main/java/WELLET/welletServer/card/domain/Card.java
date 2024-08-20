package WELLET.welletServer.card.domain;

import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.card.dto.MyCardUpdateDto;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.common.BaseTimeEntity;
import WELLET.welletServer.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseTimeEntity {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String company;

    private String role;

    @NotBlank
    private String phone;
    private String email;

    private String tel;

    private String address;
    private String memo;

    @OneToMany(mappedBy = "card")
    private List<CategoryCard> categoryCards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long ownerId;

    @Builder
    public Card(String name, String company, String role, String phone, String email, String tel, String address, String memo, List<CategoryCard> categoryCards, Member member, Long ownerId) {
        this.name = name;
        this.company = company;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.memo = memo;
        this.categoryCards = categoryCards;
        this.member = member;
        this.ownerId = ownerId;
    }

    public void updateCard(CardUpdateDto dto) {
        this.name = dto.getName();
        this.company = dto.getCompany();
        this.role = dto.getRole();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.tel = dto.getTel();
        this.address = dto.getAddress();
        this.memo = dto.getMemo();
    }

    public void updateCard(MyCardUpdateDto dto) {
        this.name = dto.getName();
        this.company = dto.getCompany();
        this.role = dto.getRole();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.tel = dto.getTel();
        this.address = dto.getAddress();
    }

    public void addCardCategory(List<CategoryCard> categoryCards) {
        this.categoryCards = categoryCards;
    }
}
