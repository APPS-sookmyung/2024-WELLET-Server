package WELLET.welletServer.member.dto;

import WELLET.welletServer.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {
    @Schema(description = "수정할 이름", example = "홍길덩") @NotBlank private String username;
    @Schema(description = "수정할 닉네임", example = "홍길당") @NotBlank private String nickname;
//    private String profile_image;

    public static MemberUpdateDto toMemberUpdateDto(Member member) {
        return MemberUpdateDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
//                .profile_image(member.getProfile_image())
                .build();
    }
}

