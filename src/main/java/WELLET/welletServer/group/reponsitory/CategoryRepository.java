package WELLET.welletServer.group.reponsitory;

import WELLET.welletServer.group.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
