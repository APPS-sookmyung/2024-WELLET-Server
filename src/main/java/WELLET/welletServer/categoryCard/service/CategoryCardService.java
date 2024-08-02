package WELLET.welletServer.categoryCard.service;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.categoryCard.repository.CategoryCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryCardService {

    private final CategoryCardRepository categoryCardRepository;

    @Transactional
    public CategoryCard save(Category category, Card card) {
        return CategoryCard.builder()
                .category(category)
                .card(card)
                .build();
    }

    @Transactional
    public List<CategoryCard> categoryToCategoryCardList(Card card, List<Category> categories) {
        List<CategoryCard> categoryCards = new ArrayList<>();

        for (Category category : categories) {
            CategoryCard categoryCard = CategoryCard.builder()
                    .category(category)
                    .card(card)
                    .build();
            categoryCards.add(categoryCard);
        }

        categoryCardRepository.saveAll(categoryCards);

        return categoryCards;
    }

    public List<String> categoryNames(List<CategoryCard> categoryCards) {
        List<String> categoryNames = new ArrayList<>();
        for (CategoryCard categoryCard : categoryCards) {
            String name = categoryCard.getCategory().getName();
            categoryNames.add(name);
        }
        return categoryNames;
    }
}
