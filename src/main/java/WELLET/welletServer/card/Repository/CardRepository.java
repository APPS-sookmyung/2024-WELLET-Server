package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.member = :member AND (c.name LIKE %:keyword% OR c.company LIKE %:keyword% OR c.position LIKE %:keyword% OR c.department LIKE %:keyword% OR c.memo LIKE %:keyword%)")
    List<Card> searchCardsAndMember(String keyword, Member member);

    List<Card> findByMember(Member member);

    List<Card> findByCategoryAndMember(Category category, Member member);

    Optional<Card> findByOwnerId(Long ownerId);

}
