package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT c FROM Card c WHERE c.name LIKE %:keyword%")
    List<Card> searchCardsByName(String keyword);

    Optional<Card> findByOwnerId(Long ownerId);
}
