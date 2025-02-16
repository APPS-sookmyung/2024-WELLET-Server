package WELLET.welletServer.card.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CardUpdateDtoProfImg {
    @Schema(description = "프로필 이미지", example = "https://bucket-name.s3.amazonaws.com")
    private MultipartFile profImg;

}
