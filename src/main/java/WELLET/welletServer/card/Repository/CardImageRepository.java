package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CardImageRepository extends JpaRepository<CardImage, Long> {
    CardImage findByCard(Card card);
}
