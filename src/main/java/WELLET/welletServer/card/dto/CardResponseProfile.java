package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardResponseProfile(
        @Schema(description = "프로필이미지 URL", example = "profimg/url") String profImgUrl)
{

    public static CardResponseProfile toCardDto(CardImage cardImage) {
        return CardResponseProfile.builder()
                .profImgUrl(cardImage != null ? cardImage.getProf_img_url() : null)
                .build();
    }
}

