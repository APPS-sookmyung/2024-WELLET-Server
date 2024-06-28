package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.dto.CardSaveDto;
import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/card")
    public BasicResponse<String> create(@Valid @RequestBody CardSaveDto cardSaveDto) {
        long cardId = cardService.saveCard(cardSaveDto);
        return ResponseUtil.success("명함 저장에 성공하였습니다. 명함 id : " + cardId);
    }

    @PutMapping("/card/{card_id}")
    public BasicResponse<CardUpdateDto> updateCard(@PathVariable Long card_id, @Valid @RequestBody CardUpdateDto dto) {
        CardUpdateDto cardUpdateDto = cardService.updateCard(card_id, dto);
        return ResponseUtil.success(cardUpdateDto);
    }

    @DeleteMapping("/card/{card_id}")
    public BasicResponse<String> deleteCard(@PathVariable Long card_id) {
        long cardId = cardService.deleteCard(card_id);
        return ResponseUtil.success("명함 삭제에 성공하였습니다. 명함 id : " + cardId);
    }
}
