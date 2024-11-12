package WELLET.welletServer.member.dto;

import WELLET.welletServer.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberListDto(@Schema(description = "id", example = "1") @NotBlank long count,
                            @Schema(description = "회원 아이디", example = "음식점") @NotBlank List<MemberDto> member) {

    public static MemberListDto toMemberList(long count, List<MemberDto> member) {
        return MemberListDto.builder()
                .count(count)
                .member(member)
                .build();
    }
}
