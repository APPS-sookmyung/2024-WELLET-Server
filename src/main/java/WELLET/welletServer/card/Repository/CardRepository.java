package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
