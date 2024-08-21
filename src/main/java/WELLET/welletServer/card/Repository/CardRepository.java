package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT c FROM Card c WHERE c.name LIKE %:keyword% or c.company LIKE %:keyword% or c.department LIKE %:keyword% or c.position LIKE %:keyword% or c.memo LIKE %:keyword%")
    List<Card> searchCards(String keyword);
}
