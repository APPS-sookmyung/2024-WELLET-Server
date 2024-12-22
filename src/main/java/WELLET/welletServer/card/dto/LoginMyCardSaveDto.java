package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginMyCardSaveDto {
    @NotBlank @Schema(description = "이름", example = "주아정")
    private String name;

    @Schema(description = "프로필 이미지파일") private String profImg;


    @Builder
    public LoginMyCardSaveDto(String name, String profile_Img) {
        this.name = name;
        this.profImg = profile_Img;
    }
}
