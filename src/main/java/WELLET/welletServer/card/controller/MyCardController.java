package WELLET.welletServer.card.controller;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.*;
import WELLET.welletServer.card.service.MyCardService;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Tag(name = "내 명함", description = "My Card API")
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
    public MyCardResponse create(HttpServletRequest request, @Valid @ModelAttribute MyCardSaveDto dto) {
        Member member = memberService.loadMember(request);

        Card card = myCardService.saveCard(member, dto);
        return MyCardResponse.toCardDto(card);
    }

    @GetMapping
    @Operation(summary = "내 명함 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    public MyCardResponse findMyCard(HttpServletRequest request) {
        Member member = memberService.loadMember(request);
        return myCardService.findMyCard(member);
    }

    @PutMapping(consumes = "multipart/form-data")
    @Operation(summary = "내 명함 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public MyCardResponseContent updateMyCard(HttpServletRequest request, @Valid @ModelAttribute MyCardUpdateDtoContent dto) {
        Member member = memberService.loadMember(request);
        return myCardService.updateMyCardContent(member, dto);
    }

    @PutMapping(value = "/images", consumes = "multipart/form-data")
    @Operation(summary = "내 명함 수정 - 프로필 이미지 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 수정 - 프로필 이미지 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public MyCardResponseProfImg updateMyCardWithProfImg(HttpServletRequest request, @Valid @ModelAttribute MyCardUpdateDtoProfImg dto) {
        Member member = memberService.loadMember(request);
        return myCardService.updateMyCardProfImg(member, dto);
    }

    @DeleteMapping
    @Operation(summary = "내 명함 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 명함 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "명함을 찾을 수 없습니다."),
    })
    public String deleteMyCard(HttpServletRequest request) {
        Member member = memberService.loadMember(request);
        myCardService.deleteMyCard(member);
        return "내 명함 삭제에 성공하였습니다.";
    }
}
