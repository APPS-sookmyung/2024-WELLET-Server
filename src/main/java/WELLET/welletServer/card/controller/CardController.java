package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.CardCountResponseDto;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.dto.CardSaveDto;
import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.categoryCard.service.CategoryCardService;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Tag(name = "명함", description = "Card API")
public class CardController {
    private final CardService cardService;
    private final MemberService memberService;
    private final CategoryCardService categoryCardService;

    @PostMapping("/{member_id}")
    @Operation(summary = "명함 생성")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public BasicResponse<CardResponse> create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody CardSaveDto dto) {
        Member member = memberService.findMember(memberId);
        if (dto.getCategoryNames() != null) {
            return ResponseUtil.success(categoryCardService.createCardWithCategory(dto, member));
        }
        Card card = cardService.saveCard(member, dto);
        return ResponseUtil.success(CardResponse.toCardDto(card, dto.getCategoryNames()));
    }

    @GetMapping
    @Operation(summary = "전체 명함 조회")
    public BasicResponse<CardCountResponseDto> findAllCards() {
        return ResponseUtil.success(cardService.findAllCard());
    }

    @GetMapping("/{card_id}")
    @Operation(summary = "명함 단건 조회")
    @Parameters({
            @Parameter(name = "card_id", example = "1"),
    })
    public BasicResponse<CardResponse> findCard(@PathVariable(name = "card_id") Long cardId) {
        return ResponseUtil.success(cardService.findCard(cardId));
    }

    @PutMapping("/{card_id}")
    @Operation(summary = "명함 수정")
    @Parameters({
            @Parameter(name = "card_id", example = "1"),
    })
    public BasicResponse<CardUpdateDto> updateCard(@PathVariable Long card_id, @Valid @RequestBody CardUpdateDto dto) {
        CardUpdateDto cardUpdateDto = cardService.updateCard(card_id, dto);
        return ResponseUtil.success(cardUpdateDto);
    }

    @DeleteMapping("/{card_id}")
    @Operation(summary = "명함 삭제")
    @Parameters({
            @Parameter(name = "card_id", example = "1"),
    })
    public BasicResponse<String> deleteCard(@PathVariable Long card_id) {
        Card card = cardService.findOne(card_id);
        categoryCardService.delete(card);
        return ResponseUtil.success("명함 삭제에 성공하였습니다. 명함 id : " + card_id);
    }

    @PostMapping
    @Operation(summary = "명함 동시 삭제")
    @Parameters({
            @Parameter(name = "cards_id", example = "[1, 2]"),
    })
    public BasicResponse<String> deleteCardList(@RequestBody List<Long> cards_id) {
        cardService.deleteCardList(cards_id);
        return ResponseUtil.success("명함 동시 삭제에 성공하였습니다. 명함 id : " + cards_id);
    }
}
