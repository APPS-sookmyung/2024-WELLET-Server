package WELLET.welletServer.member.service;

import WELLET.welletServer.files.S3FileUploader;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.dto.MemberSaveDto;
import WELLET.welletServer.member.dto.MemberUpdateDto;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import WELLET.welletServer.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3FileUploader s3FileUploader;
    @Transactional
    public long saveMember (MemberSaveDto dto) {
        // Username 중복 체크
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_USERNAME);
        }

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

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
