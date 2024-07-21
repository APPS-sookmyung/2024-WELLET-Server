package WELLET.welletServer.group.reponsitory;

import WELLET.welletServer.group.domain.Category;
import WELLET.welletServer.group.domain.CategoryCard;
import WELLET.welletServer.group.dto.CategoryListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT cc FROM CategoryCard cc WHERE cc.category.id = :categoryId")
    List<CategoryCard> findCardsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT cc FROM CategoryCard cc  WHERE cc.category.id IS NOT NULL")
    List<CategoryCard> findAllCards();
}
