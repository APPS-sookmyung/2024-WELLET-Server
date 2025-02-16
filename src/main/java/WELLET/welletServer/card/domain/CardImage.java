package WELLET.welletServer.card.domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "card_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardImage {
    @Id
    @Column(name = "card_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String front_img_url;
    private String back_img_url;
    private String prof_img_url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public CardImage(String front_img_url, String back_img_url, String prof_img_url, Card card) {
        this.front_img_url = front_img_url;
        this.back_img_url = back_img_url;
        this.prof_img_url = prof_img_url;
        this.card = card;
    }

    public void updateFrontImage(String front_img_url) {
        this.front_img_url = front_img_url;
    }
    public void updateBackImage(String back_img_url) {
        this.back_img_url = back_img_url;
    }

    public void updateProfImage(String prof_img_url) {
        this.prof_img_url = prof_img_url;
    }
}


