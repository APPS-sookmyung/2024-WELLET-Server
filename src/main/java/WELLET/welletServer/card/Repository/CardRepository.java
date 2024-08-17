package WELLET.welletServer.card.Repository;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(value = "SELECT c FROM Card c WHERE c.name LIKE %:keyword% AND c.member.id = :memberId")
    List<Card> searchCardsByName(@Param("memberId") Long memberId, String keyword);

    List<Card> findByMember(Member member);

    Optional<Card> findByOwnerId(Long ownerId);
}
