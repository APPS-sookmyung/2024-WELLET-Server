package WELLET.welletServer.member.controller;

import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.dto.MemberListDto;
import WELLET.welletServer.member.dto.MemberSaveDto;
import WELLET.welletServer.member.dto.MemberUpdateDto;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "회원 저장")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "회원 저장, 사용 X")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 저장에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "중복된 회원입니다.")
    })
    public String create(@Valid @RequestBody MemberSaveDto memberSaveDto) {

        long memberId = memberService.saveMember(memberSaveDto);
        return "회원 저장에 성공하였습니다. 회원 id: " + memberId;
    }

    @PutMapping
    @Operation(summary = "회원 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다."),
    })
    public MemberUpdateDto updateMember(HttpServletRequest request, @Valid @RequestBody MemberUpdateDto dto) {
        Member member = memberService.loadMember(request);
        return memberService.updateMember(member, dto);
    }

    @GetMapping
    @Operation(summary = "회원 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 목록 조회에 성공하였습니다."),
    })
    public MemberListDto findMemberList() {
        return memberService.findMemberList();
    }
}
