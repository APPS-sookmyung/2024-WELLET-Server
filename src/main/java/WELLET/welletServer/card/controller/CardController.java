package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.*;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.category.service.CategoryService;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.categoryCard.service.CategoryCardService;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final MemberService memberService;
    private final CategoryCardService categoryCardService;
    private final CategoryService categoryService;

    @PostMapping("/{member_id}")
    public BasicResponse<CardResponse> create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody CardSaveDto dto) {
        Member member = memberService.findMember(memberId);
        List<Category> categories = null;
        if (dto.getCategoryNames() != null) {
            categories = categoryService.findCategoryNames(dto.getCategoryNames());
            Card card = cardService.saveCard(member, dto);
            List<CategoryCard> categoryList = categoryCardService.categoryToCategoryCardList(card, categories);
            CardResponse cardResponse = cardService.addCategory(card, categoryList, dto.getCategoryNames());
            return ResponseUtil.success(cardResponse);
        }
        Card card = cardService.saveCard(member, dto);
        return ResponseUtil.success(CardResponse.toCardDto(card, dto.getCategoryNames()));
//        return null;
    }

    @GetMapping
    public BasicResponse<CardCountResponseDto> findAllCards() {
        return ResponseUtil.success(cardService.findAllCard());
    }

    @GetMapping("/{card_id}")
    public BasicResponse<CardResponse> findCard(@PathVariable(name = "card_id") Long cardId) {
        return ResponseUtil.success(cardService.findCard(cardId));
    }

    @PutMapping("/{card_id}")
    public BasicResponse<CardUpdateDto> updateCard(@PathVariable Long card_id, @Valid @RequestBody CardUpdateDto dto) {
        CardUpdateDto cardUpdateDto = cardService.updateCard(card_id, dto);
        return ResponseUtil.success(cardUpdateDto);
    }

    @DeleteMapping("/{card_id}")
    public BasicResponse<String> deleteCard(@PathVariable Long card_id) {
        long cardId = cardService.deleteCard(card_id);
        return ResponseUtil.success("명함 삭제에 성공하였습니다. 명함 id : " + cardId);
    }

    @PostMapping
    public BasicResponse<String> deleteCardList(@RequestBody List<Long> cards_id) {
        cardService.deleteCardList(cards_id);
        return ResponseUtil.success("명함 동시 삭제에 성공하였습니다. 명함 id : " + cards_id);
    }
}
