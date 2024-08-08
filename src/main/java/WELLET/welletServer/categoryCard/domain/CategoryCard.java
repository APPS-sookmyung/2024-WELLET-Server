package WELLET.welletServer.categoryCard.domain;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.domain.Category;
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
    @Column(name = "category_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Builder
    public CategoryCard(Category category, Card card) {
        this.category = category;
        this.card = card;
    }
}