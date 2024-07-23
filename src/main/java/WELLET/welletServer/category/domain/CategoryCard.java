package WELLET.welletServer.category.domain;

import WELLET.welletServer.card.domain.Card;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table (name = "CategoryCard")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class CategoryCard {

    @Id
    @Column(name = "group_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_company")
    private String cardCompany;

    @Builder
    public CategoryCard(Long cardId) {
        this.cardName = cardName;
        this.cardCompany = cardCompany;
    }

}