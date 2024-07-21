package WELLET.welletServer.group.domain;

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

    @Column(name = "card_id", nullable = false)
    private Long cardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_company")
    private String cardCompany;

    @Builder
    public CategoryCard(Long cardId) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.cardCompany = cardCompany;
    }


}