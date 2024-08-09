package WELLET.welletServer.category.reponsitory;

import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT cc FROM CategoryCard cc WHERE cc.category = :category")
    List<CategoryCard> findCardsByCategory(@Param("category") Category category);

    @Query("SELECT cc FROM CategoryCard cc  WHERE cc.category.id IS NOT NULL")
    List<CategoryCard> findAllCards();

    Optional<Category> findByName(String name);
}
