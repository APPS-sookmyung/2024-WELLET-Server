package WELLET.welletServer.category.reponsitory;

import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByMember(Member member);
    Optional<Category> findByMemberAndName(Member member, String name);
    Optional<Category> findByName(String name);
}
