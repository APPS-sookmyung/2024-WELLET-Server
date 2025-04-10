package WELLET.welletServer.member.service;

import WELLET.welletServer.kakaologin.dto.KakaoUserInfoResponseDto;
import WELLET.welletServer.kakaologin.jwt.JwtService;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.dto.MemberDto;
import WELLET.welletServer.member.dto.MemberListDto;
import WELLET.welletServer.member.dto.MemberSaveDto;
import WELLET.welletServer.member.dto.MemberUpdateDto;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import WELLET.welletServer.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    @Transactional
    public long saveMember (MemberSaveDto dto) {
        // Username 중복 체크
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_USERNAME);
        }

        Member member = Member.builder()
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional
    public Member saveMember (KakaoUserInfoResponseDto dto) {
        Member member = Member.builder()
                .kakaoId(dto.getId())
                .username(UUID.randomUUID())
                .nickname(dto.getKakaoAccount().getProfile().getNickName())
                .profileImage(dto.getKakaoAccount().getProfile().getProfileImageUrl())
                .lastLoginTime(LocalDateTime.now())  // 최초 로그인 시간 설정
                .build();
        return memberRepository.save(member);
    }

    @Transactional
    public MemberUpdateDto updateMember(Member member, MemberUpdateDto dto) {
        member.updateMember(dto);
        return MemberUpdateDto.toMemberUpdateDto(member);
    }

    public MemberListDto findMemberList() {

        List<MemberDto> members = new ArrayList<>();
        for (Member member : memberRepository.findAll()) {
            members.add(MemberDto.toMemberDto(member));
        }
        return MemberListDto.toMemberList(members.size(), members);
    }

    public Member loadMember(HttpServletRequest header){
        String token = jwtService.getJwtToCookie(header);
        UUID username = UUID.fromString(jwtService.getUsernameFromToken(token));
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
