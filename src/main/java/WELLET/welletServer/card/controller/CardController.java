package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.dto.CardListResponse;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.dto.CardSaveDto;
import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.card.service.CardService;
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

    @PostMapping("/{member_id}")
    public BasicResponse<String> create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody CardSaveDto cardSaveDto) {
        Member member = memberService.findMember(memberId);
        long cardId = cardService.saveCard(member, cardSaveDto);
        return ResponseUtil.success("명함 저장에 성공하였습니다. 명함 id : " + cardId);
    }

    @GetMapping
    public BasicResponse<List<CardListResponse>> findAllCards() {
        return ResponseUtil.success(cardService.findAllCard());
    }

    @GetMapping("/{card_id}")
    public BasicResponse<CardResponse> findCard(@PathVariable(name = "card_id") Long cardId) {
        return ResponseUtil.success(cardService.findOne(cardId));
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
}
