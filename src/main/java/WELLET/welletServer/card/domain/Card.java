package WELLET.welletServer.card.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String position;

    @NotNull
    private String email;
    private String phone;
    private String tel;
    private String department;

    @NotNull
    private String company;
    private String address;
    private String memo;

//    @NotNull
    private String qr;
    private String profile_image;

    @Builder
    public Card(String name, String position, String email, String phone, String tel, String department, String company, String address, String memo) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.department = department;
        this.company = company;
        this.address = address;
        this.memo = memo;
    }
}
