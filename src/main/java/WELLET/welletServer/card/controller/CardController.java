package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import WELLET.welletServer.card.dto.*;
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
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "명함 생성")
    @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "명함 생성에 성공하였습니다."),
         @ApiResponse(responseCode = "400", description = "그룹을 찾을 수 없습니다."),
         @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    public CardResponse create(HttpServletRequest request, @Valid @ModelAttribute CardSaveDto dto) {
        Member member = memberService.loadMember(request);
        if (dto.getCategoryName() == null) {
            throw new CardException(CardErrorCode.CATEGORY_NOT_SELECTED);
        }
        Category category = categoryService.findByName(member, dto.getCategoryName());
        Card card = cardService.saveCard(member, category, dto);
        CardImage cardImage = cardService.saveCardImage(card, dto);
        return CardResponse.toCardDto(card, dto.getCategoryName(), cardImage);
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "명함 단건 조회")
    @Parameters({
            @Parameter(name = "cardId", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 단건 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public CardResponse findCard(HttpServletRequest request, @PathVariable(name = "cardId") Long cardId) {
        Member member = memberService.loadMember(request);
        return cardService.findCard(member, cardId);
    }

    @PutMapping(value = "/{cardId}", consumes = "multipart/form-data")
    @Operation(summary = "명함 수정")
    @Parameters({
            @Parameter(name = "cardId", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public CardResponse updateCard(HttpServletRequest request, @PathVariable Long cardId, @Valid @ModelAttribute CardUpdateDto dto) {
        Member member = memberService.loadMember(request);

        Card card = cardService.updateCard(member, cardId, dto);
        return CardResponse.toCardDto(card, dto.getCategoryName(), null);
    }

    @PutMapping(value = "/{cardId}/prof/images", consumes = "multipart/form-data")
    @Operation(summary = "명함 수정 - 프로필 수정")
    @Parameters({
            @Parameter(name = "cardId", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 이미지 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public CardResponse updateCardImage(HttpServletRequest request, @PathVariable Long cardId, @Valid @ModelAttribute CardUpdateDtoProfImg dto) {
        Member member = memberService.loadMember(request);
        Card card = cardService.findOne(member, cardId);

        CardImage cardImage = cardService.updateCardImage(card, dto);
        return CardResponse.toCardDto(card, null, cardImage);
    }

    @PatchMapping
    @Operation(summary = "명함 동시 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 동시 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "X번 명함이 존재하지 않습니다."),
    })
    public String deleteCardList(HttpServletRequest request, @RequestBody List<Long> cardsId) {
        Member member = memberService.loadMember(request);
        cardService.deleteCardList(member, cardsId);
        return "명함 동시 삭제에 성공하였습니다. 명함 id : " + cardsId;
    }

    @GetMapping
    @Operation(summary = "명함 전체 조회 및 검색")
    @Parameters({
            @Parameter(name = "keyword", example = "ajeong"),
      })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 검색에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
      })
    public CardCountResponseDto searchCards (
            HttpServletRequest request, @RequestParam(value = "keyword", required = false) String keyword) {

        if (keyword == null || keyword.isEmpty()) {
            Member member = memberService.loadMember(request);
            return cardService.findAllCard(member);
        } else {
            memberService.loadMember(request);
            return cardService.searchCards(keyword);
    }}


    @GetMapping("categories/{categoryId}")
    @Operation(summary = "그룹별 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "그룹을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "categoryId", description = "공백 X", example = "1"),
    })
    public CardCountResponseDto findCardsByCategoryId(HttpServletRequest request, @PathVariable Long categoryId) {
        Member member = memberService.loadMember(request);
        Category category = categoryService.findById(categoryId);
        return cardService.findByCategory(member, category);
    }
}
