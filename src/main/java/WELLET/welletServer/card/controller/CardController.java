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
    private final CategoryCardService categoryCardService;

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
    public BasicResponse<CardResponse> create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody CardSaveDto dto) {
        Member member = memberService.findMember(memberId);
        if (dto.getCategoryNames() != null) {
            return ResponseUtil.success(categoryCardService.createCardWithCategory(dto, member));
        }
        Card card = cardService.saveCard(member, dto);
        return ResponseUtil.success(CardResponse.toCardDto(card, dto.getCategoryNames()));
    }

    @GetMapping("/{member_id}")
    @Operation(summary = "전체 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 전체 조회에 성공하였습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public BasicResponse<CardCountResponseDto> findAllCards(@PathVariable Long member_id) {
        Member member = memberService.findMember(member_id);
        return ResponseUtil.success(cardService.findAllCard(member));
    }

    @GetMapping("/{member_id}/{card_id}")
    @Operation(summary = "명함 단건 조회")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "card_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 단건 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함이 존재하지 않습니다."),
    })
    public BasicResponse<CardResponse> findCard(@PathVariable(name = "card_id") Long cardId) {
        return ResponseUtil.success(cardService.findCard(cardId));
    }

    @PutMapping("/{card_id}")
    @Operation(summary = "명함 수정")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "card_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함이 존재하지 않습니다."),
    })
    public BasicResponse<CardUpdateDto> updateCard(@PathVariable Long card_id, @Valid @RequestBody CardUpdateDto dto) {
        CardUpdateDto cardUpdateDto = cardService.updateCard(card_id, dto);
        return ResponseUtil.success(cardUpdateDto);
    }

    @DeleteMapping("/{card_id}")
    @Operation(summary = "명함 삭제")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "card_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 삭제 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함이 존재하지 않습니다."),
    })
    public BasicResponse<String> deleteCard(@PathVariable Long card_id) {
        Card card = cardService.findOne(card_id);
        categoryCardService.delete(card);
        return ResponseUtil.success("명함 삭제에 성공하였습니다. 명함 id : " + card_id);
    }

    @PostMapping
    @Operation(summary = "명함 동시 삭제")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "cards_id", example = "[1, 2]"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "명함 동시 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함이 존재하지 않습니다."),
    })
    public BasicResponse<String> deleteCardList(@RequestBody List<Long> cards_id) {
//        cardService.deleteCardList(cards_id);
        List<Card> cardList = cardService.findCardList(cards_id);
        categoryCardService.deleteCardList(cardList);
        return ResponseUtil.success("명함 동시 삭제에 성공하였습니다. 명함 id : " + cards_id);
    }

    @GetMapping("/{member_id}/search")
    @Operation(summary = "이름으로 명함 검색")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "keyword", example = "주아정"),
      })
    @ApiResponses(value = {
              @ApiResponse(responseCode = "200", description = "명함 검색에 성공하였습니다."),
      })
    public BasicResponse<CardCountResponseDto> searchCardsByName (
            @PathVariable Long member_id, @RequestParam(value = "keyword", required = false) String keyword) {

        if (keyword == null || keyword.isEmpty()) {
            Member member = memberService.findMember(member_id);
            return ResponseUtil.success(cardService.findAllCard(member));
        } else {
            return ResponseUtil.success(cardService.searchCardsByName(member_id, keyword));
    }}
}
