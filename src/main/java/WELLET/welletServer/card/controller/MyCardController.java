package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.dto.CardUpdateDto;
import WELLET.welletServer.card.dto.MyCardUpdateDto;
import WELLET.welletServer.card.service.MyCardService;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.card.dto.MyCardSaveDto;
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

@RestController
@RequestMapping("/me/{member_id}")
@RequiredArgsConstructor
@Tag(name = "명함", description = "My Card API")
public class MyCardController {

    private final MyCardService myCardService;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "내 명함 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "내 명함이 이미 존재합니다."),
            @ApiResponse(responseCode = "400", description = "멤버가 존재하지 않습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public BasicResponse<CardResponse> create(@PathVariable(name = "member_id") Long memberId, @Valid @RequestBody MyCardSaveDto dto) {
        memberService.findMember(memberId);
        Card card = myCardService.saveCard(memberId, dto);
        return ResponseUtil.success(CardResponse.toCardDto(card));
    }

    @GetMapping
    @Operation(summary = "내 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    public BasicResponse<CardResponse> findMyCard(@PathVariable(name = "member_id") Long memberId) {
        return ResponseUtil.success(myCardService.findMyCard(memberId));
    }

    @PutMapping
    @Operation(summary = "내 명함 수정")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함이 존재하지 않습니다."),
    })
    public BasicResponse<MyCardUpdateDto> updateMyCard(@PathVariable Long member_id, @Valid @RequestBody MyCardUpdateDto dto) {
        MyCardUpdateDto myCardUpdateDto = myCardService.updateMyCard(member_id, dto);
        return ResponseUtil.success(myCardUpdateDto);
    }

    @DeleteMapping
    @Operation(summary = "내 명함 삭제")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "명함이 존재하지 않습니다."),
    })
    public BasicResponse<String> deleteMyCard(@PathVariable Long member_id) {
        myCardService.deleteMyCard(member_id);
        return ResponseUtil.success("내 명함 삭제에 성공하였습니다.");
    }
}