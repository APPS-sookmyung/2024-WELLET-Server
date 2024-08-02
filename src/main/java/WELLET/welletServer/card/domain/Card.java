package WELLET.welletServer.card.domain;

import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.category.domain.CategoryCard;
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
public class Card {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String position;

    @NotBlank
    private String email;
    private String phone;
    private String tel;
    private String department;

    @NotBlank
    private String company;
    private String address;
    private String memo;

    @OneToMany(mappedBy = "card")
    private List<CategoryCard> categoryCards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    @NotNull
    private String qr;
    private String profile_image;

    @Builder
    public Card(String name, String position, String email, String phone, String tel, String department, String company, String address, String memo, Member member) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.department = department;
        this.company = company;
        this.address = address;
        this.memo = memo;
        this.member = member;
    }

    public void updateCard(CardUpdateDto dto) {
        this.name = dto.getName();
        this.position = dto.getPosition();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.tel = dto.getTel();
        this.department = dto.getDepartment();
        this.company = dto.getCompany();
        this.address = dto.getAddress();
        this.memo = dto.getMemo();
    }
}
