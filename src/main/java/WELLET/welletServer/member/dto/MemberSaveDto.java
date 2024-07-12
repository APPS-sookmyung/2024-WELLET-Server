package WELLET.welletServer.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveDto {

    @NotBlank
    private String username;
    private String nickname;
    private String password;
//    private String profile_image;

    @Builder
    public MemberSaveDto(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
//        this.profile_image = profile_image;
    }
}