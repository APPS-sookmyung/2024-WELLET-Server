package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Builder
public class CardUpdateDtoBackFrontImgDto {

    @Schema(description = "명함앞 이미지", example = "https://bucket-name.s3.amazonaws.com")  private MultipartFile frontImg;

    @Schema(description = "명함뒤 이미지", example = "https://bucket-name.s3.amazonaws.com")  private MultipartFile backImg;
}
