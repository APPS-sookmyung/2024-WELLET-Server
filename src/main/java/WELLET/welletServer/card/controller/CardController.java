package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.CardCountResponseDto;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.dto.CardSaveDto;
import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.category.service.CategoryService;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final CategoryService categoryService;

    @PostMapping("/{member_id}")
    @Operation(summary = "명함 생성")
    @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "명함 생성에 성공하였습니다."),
         @ApiResponse(responseCode = "400", description = "그룹을 찾을 수 없습니다."),
         @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public CardResponse create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody CardSaveDto dto) {
        Member member = memberService.findMember(memberId);
        if (dto.getCategoryName() == null) {
            throw new CardException(CardErrorCode.CATEGORY_NOT_SELECTED);
        }
        Category category = categoryService.findByName(dto.getCategoryName());
        Card card = cardService.saveCard(member, category, dto);
        return CardResponse.toCardDto(card, dto.getCategoryName());
    }

    @GetMapping("/{member_id}")
    @Operation(summary = "전체 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 전체 조회에 성공하였습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public CardCountResponseDto findAllCards(@PathVariable Long member_id) {
        Member member = memberService.findMember(member_id);
        return cardService.findAllCard(member);
    }

    @GetMapping("/{member_id}/{card_id}")
    @Operation(summary = "명함 단건 조회")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "card_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 단건 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public CardResponse findCard(@PathVariable(name = "card_id") Long cardId) {
        return cardService.findCard(cardId);
    }

    @PutMapping("/{card_id}")
    @Operation(summary = "명함 수정")
    @Parameters({
            @Parameter(name = "card_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public CardUpdateDto updateCard(@PathVariable Long card_id, @Valid @RequestBody CardUpdateDto dto) {
        return cardService.updateCard(card_id, dto);
    }

    @DeleteMapping("/{card_id}")
    @Operation(summary = "명함 삭제")
    @Parameters({
            @Parameter(name = "card_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public String deleteCard(@PathVariable Long card_id) {
        cardService.deleteCard(card_id);
        return "명함 삭제에 성공하였습니다. 명함 id : " + card_id;
    }

    @PostMapping
    @Operation(summary = "명함 동시 삭제")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 동시 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "X번 명함이 존재하지 않습니다."),
    })
    public String deleteCardList(@RequestBody List<Long> cards_id) {
        cardService.deleteCardList(cards_id);
        return "명함 동시 삭제에 성공하였습니다. 명함 id : " + cards_id;
    }

    @GetMapping("/{member_id}/search")
    @Operation(summary = "명함 검색")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "keyword", example = "ajeong"),
      })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 검색에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
      })
    public CardCountResponseDto searchCards (
            @PathVariable Long member_id, @RequestParam(value = "keyword", required = false) String keyword) {

        if (keyword == null || keyword.isEmpty()) {
            Member member = memberService.findMember(member_id);
            return cardService.findAllCard(member);
        } else {
            memberService.findMember(member_id);
            return cardService.searchCards(keyword);
    }}


    @GetMapping("categories/{memberId}/{categoryId}")
    @Operation(summary = "그룹별 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "그룹을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", description = "공백 X", example = "1"),
            @Parameter(name = "category_id", description = "공백 X", example = "1"),
    })
    public CardCountResponseDto findCardsByCategoryId(@PathVariable Long memberId, @PathVariable Long categoryId) {
        Member member = memberService.findMember(memberId);
        Category category = categoryService.findById(categoryId);
        return cardService.findByCategory(member, category);
    }


}
