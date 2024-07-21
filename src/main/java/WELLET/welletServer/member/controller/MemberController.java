package WELLET.welletServer.member.controller;

import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.member.dto.MemberSaveDto;
import WELLET.welletServer.member.dto.MemberUpdateDto;
import WELLET.welletServer.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public BasicResponse<String> create(@Valid @RequestBody MemberSaveDto memberSaveDto) {
        long memberId = memberService.saveMember(memberSaveDto);
        return ResponseUtil.success("회원 저장에 성공하였습니다. 화원 id: " + memberId);
    }

    @PutMapping ("/{member_id}")
    public BasicResponse<MemberUpdateDto> updateMember(@PathVariable(name = "member_id") Long member_id, @Valid @RequestBody MemberUpdateDto dto) {
        MemberUpdateDto memberUpdateDto = memberService.updateMember(member_id, dto);
        return ResponseUtil.success(memberUpdateDto);
    }

//    @PutMapping("/profile/image")
//    public BasicResponse<MemberUpdateDto> updateMemberImage(@RequestParam Long Member_id, @Valid @RequestBody MemberUpdateDto dto) {
//        MemberUpdateDto memberUpdateDto = memberService.updateMemberImage(member_id, dto);
//        return ResponseUtil.success(memberUpdateDto);
//    }
}
