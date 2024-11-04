package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT c FROM Card c WHERE c.name LIKE %:keyword% or c.company LIKE %:keyword% or c.role LIKE %:keyword% or c.memo LIKE %:keyword%")
    List<Card> searchCards(String keyword);

    List<Card> findByMember(Member member);

    List<Card> findByCategoryAndMember(Category category, Member member);

    Optional<Card> findByOwnerId(Long ownerId);

}
