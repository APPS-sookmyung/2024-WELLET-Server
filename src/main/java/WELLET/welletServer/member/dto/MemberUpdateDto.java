package WELLET.welletServer.member.dto;

import WELLET.welletServer.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {

    private String username;

    private String nickname;

//    private String profile_image;

    public static MemberUpdateDto toMemberUpdateDto(Member member) {
        return MemberUpdateDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
//                .profile_image(member.getProfile_image())
                .build();
    }
}

