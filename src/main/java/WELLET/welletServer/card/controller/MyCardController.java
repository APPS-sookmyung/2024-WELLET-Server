package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.service.CardService;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.member.dto.SaveMyCard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Tag(name = "명함", description = "My Card API")
public class MyCardController {

    private final CardService cardService;

    @PostMapping("/{member_id}")
    @Operation(summary = "내 명함 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "내 명함이 이미 존재합니다."),
            @ApiResponse(responseCode = "400", description = "멤버가 존재하지 않습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public BasicResponse<CardResponse> create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody SaveMyCard dto) {
        Card card = cardService.saveCard(memberId, dto);
        return ResponseUtil.success(CardResponse.toCardDto(card));
    }

    @GetMapping("/{member_id}")
    @Operation(summary = "내 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public BasicResponse<CardResponse> findMyCard(@PathVariable(name = "member_id") Long memberId) {
        return ResponseUtil.success(cardService.findMyCard(memberId));
    }
}
