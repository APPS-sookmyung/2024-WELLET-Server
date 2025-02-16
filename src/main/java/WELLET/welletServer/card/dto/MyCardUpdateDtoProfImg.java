package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Builder
public class MyCardUpdateDtoProfImg {
    @Schema(description = "프로필 이미지") private MultipartFile profImg;
}
