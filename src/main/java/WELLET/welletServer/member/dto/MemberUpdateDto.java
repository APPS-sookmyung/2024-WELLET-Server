package WELLET.welletServer.member.dto;

import WELLET.welletServer.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberUpdateDto {

    private String username;

    private String nickname;

    private String password;

//    private String profile_image;

    public static MemberUpdateDto toMemberUpdateDto(Member member) {
        return MemberUpdateDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .password(member.getPassword())
                // TODO: 패스워드 바디에서 꺼내야 함.
//                .profile_image(member.getProfile_image())
                .build();
    }
}

