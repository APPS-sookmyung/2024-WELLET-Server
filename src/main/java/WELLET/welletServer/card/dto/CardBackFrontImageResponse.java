package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CardBackFrontImageResponse (
    @Schema(description = "명함앞이미지 URL", example = "frontimg/url") String frontImgUrl,
    @Schema(description = "명함뒤이미지 URL", example = "backimg/url") String backImgUrl){

    public static CardBackFrontImageResponse toCardBackFrontImageDto(Card card, CardImage cardImage) {
        return CardBackFrontImageResponse.builder()
                .frontImgUrl(cardImage != null ? cardImage.getFront_img_url() : null)
                .backImgUrl(cardImage != null ? cardImage.getBack_img_url() : null)
                .build();
    }
}
