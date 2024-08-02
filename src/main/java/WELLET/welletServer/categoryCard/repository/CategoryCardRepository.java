package WELLET.welletServer.categoryCard.repository;

import WELLET.welletServer.categoryCard.domain.CategoryCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryCardRepository extends JpaRepository<CategoryCard, Long> {
}
