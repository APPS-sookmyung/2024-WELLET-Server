package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardResponseProfile(
        @Schema(description = "프로필이미지 URL", example = "profimg/url") String profImgUrl,
        @Schema(description = "명함앞이미지 URL", example = "frontimg/url") String frontImgUrl,
        @Schema(description = "명함뒤이미지 URL", example = "backimg/url") String backImgUrl)
{

    public static CardResponseProfile toCardDto(CardImage cardImage) {
        return CardResponseProfile.builder()
                .profImgUrl(cardImage != null ? cardImage.getProf_img_url() : null)
                .frontImgUrl(cardImage != null ? cardImage.getFront_img_url() : null)
                .backImgUrl(cardImage != null ? cardImage.getBack_img_url() : null)
                .build();
    }
}

