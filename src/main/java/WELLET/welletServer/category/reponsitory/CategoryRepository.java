package WELLET.welletServer.category.reponsitory;

import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT cc FROM CategoryCard cc WHERE cc.category.id = :categoryId AND cc.category.member.id = :memberId")
    List<CategoryCard> findCardsByIds(@Param("memberId") Long memberId, @Param("categoryId") Long categoryId);


    @Query("SELECT cc FROM CategoryCard cc WHERE cc.category.member.id = :memberId")
    List<CategoryCard> findAllCards(@Param("memberId") Long memberId);

    List<Category> findByMember(Member member);
    Optional<Category> findByMemberAndName(Member member, String name);
}
