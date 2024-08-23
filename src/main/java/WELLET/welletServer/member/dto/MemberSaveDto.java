package WELLET.welletServer.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveDto {
    @Schema(description = "사용자 이름", example = "홍길동") @NotBlank private String username;
    @Schema(description = "닉네임", example = "홍홍길길") @NotBlank private String nickname;
    @Schema(description = "비밀번호", example = "password") @NotBlank private String password;
//    private String profile_image;

    @Builder
    public MemberSaveDto(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
//        this.profile_image = profile_image;
    }
}