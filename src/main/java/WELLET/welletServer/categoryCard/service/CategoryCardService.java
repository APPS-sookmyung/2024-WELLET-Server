package WELLET.welletServer.categoryCard.service;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryCardService {

    @Transactional
    public CategoryCard save(Category category, Card card) {
        return CategoryCard.builder()
                .category(category)
                .card(card)
                .build();
    }

    @Transactional
    public List<CategoryCard> nameToCategoryCardList(List<String> categoryCardList) {
        return null;
    }
}
