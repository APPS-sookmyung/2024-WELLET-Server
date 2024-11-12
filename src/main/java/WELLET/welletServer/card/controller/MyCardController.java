package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.MyCardResponse;
import WELLET.welletServer.card.dto.MyCardSaveDto;
import WELLET.welletServer.card.dto.MyCardUpdateDto;
import WELLET.welletServer.card.service.MyCardService;
import WELLET.welletServer.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/me/{memberId}")
@RequiredArgsConstructor
@Tag(name = "명함", description = "My Card API")
public class MyCardController {

    private final MyCardService myCardService;
    private final MemberService memberService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "내 명함 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "내 명함이 이미 존재합니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "memberId", example = "1"),
    })
    public MyCardResponse create(@PathVariable Long memberId, @Valid @ModelAttribute MyCardSaveDto dto) throws IOException{
        memberService.findMember(memberId);

        Card card = myCardService.saveCard(memberId, dto);
        return MyCardResponse.toCardDto(card);
    }

    @GetMapping
    @Operation(summary = "내 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    @Parameters({
            @Parameter(name = "memberId", example = "1"),
    })
    public MyCardResponse findMyCard(@PathVariable Long memberId) {
        memberService.findMember(memberId);
        return myCardService.findMyCard(memberId);
    }

    @PutMapping(consumes = "multipart/form-data")
    @Operation(summary = "내 명함 수정")
    @Parameters({
            @Parameter(name = "memberId", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public MyCardResponse updateMyCard(@PathVariable Long memberId, @Valid @ModelAttribute MyCardUpdateDto dto) {
        memberService.findMember(memberId);
        return myCardService.updateMyCard(memberId, dto);
    }

    @DeleteMapping
    @Operation(summary = "내 명함 삭제")
    @Parameters({
            @Parameter(name = "memberId", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public String deleteMyCard(@PathVariable Long memberId) {
        memberService.findMember(memberId);
        myCardService.deleteMyCard(memberId);
        return "내 명함 삭제에 성공하였습니다.";
    }
}
