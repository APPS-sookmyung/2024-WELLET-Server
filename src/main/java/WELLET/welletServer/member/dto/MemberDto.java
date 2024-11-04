package WELLET.welletServer.member.dto;

import WELLET.welletServer.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberDto(@Schema(description = "id", example = "1") @NotBlank long id,
                        @Schema(description = "회원 아이디", example = "음식점") @NotBlank String username,
                        @Schema(description = "회원 닉네임", example = "음식점") @NotBlank String nickname) {

    public static MemberDto toMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();
    }
}
