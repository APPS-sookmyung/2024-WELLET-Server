package WELLET.welletServer.member.service;

import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.dto.MemberSaveDto;
import WELLET.welletServer.member.dto.MemberUpdateDto;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import WELLET.welletServer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public long saveMember (MemberSaveDto dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .build();
        return memberRepository.save(member).getId();
    }

    @Transactional
    public MemberUpdateDto updateMember(Long memberId, MemberUpdateDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        member.updateMember(dto);
        return MemberUpdateDto.toMemberUpdateDto(member);
    }
}
