package WELLET.welletServer.categoryCard.repository;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryCardRepository extends JpaRepository<CategoryCard, Long> {

    @Modifying
    @Query("DELETE FROM CategoryCard c WHERE c.card = :card")
    void deleteByAllCardId(Card card);
}
