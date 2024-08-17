package WELLET.welletServer.categoryCard.service;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.dto.CardSaveDto;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.category.service.CategoryService;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.categoryCard.repository.CategoryCardRepository;
import WELLET.welletServer.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryCardService {

    private final CategoryCardRepository categoryCardRepository;
    private final CategoryService categoryService;
    private final CardService cardService;

    @Transactional
    public CardResponse createCardWithCategory(CardSaveDto dto, Member member) {
        List<Category> categories = categoryService.findCategoryNames(dto.getCategoryNames());
        Card card = cardService.saveCard(member, dto);
        List<CategoryCard> categoryList = save(card, categories);
        return cardService.addCategory(card, categoryList, dto.getCategoryNames());
    }

    @Transactional
    public List<CategoryCard> save(Card card, List<Category> categories) {
        List<CategoryCard> categoryCardList = categories.stream()
                .map(category -> CategoryCard.builder()
                        .category(category)
                        .card(card)
                        .build())
                .collect(Collectors.toList());

        categoryCardRepository.saveAll(categoryCardList);
        return categoryCardList;
    }

    @Transactional
    public void delete(Card card) {
        categoryCardRepository.deleteByAllCardId(card);
        cardService.deleteCard(card);
    }

    @Transactional
    public void deleteCardList(List<Card> cardList) {
        cardList.forEach(this::delete);
    }
}
