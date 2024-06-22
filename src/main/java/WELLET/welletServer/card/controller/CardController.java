package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.dto.CardSaveDto;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/card")
    public BasicResponse<String> create(@Valid @RequestBody CardSaveDto cardSaveDto) {
        long cardId = cardService.save(cardSaveDto);
        return ResponseUtil.success("명함 저장에 성공하였습니다. 명함 id : " + cardId);
    }
}
