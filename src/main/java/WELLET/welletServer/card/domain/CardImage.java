package WELLET.welletServer.card.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "cardimage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardImage {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String front_img_url;
    private String back_img_url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public CardImage(String front_img_url, String back_img_url, Card card) {
        this.front_img_url = front_img_url;
        this.back_img_url = back_img_url;
        this.card = card;
    }

    public void updateCardImage(String back_img_url, String front_img_url) {
        this.front_img_url = back_img_url;
        this.back_img_url = front_img_url;
    }
}


