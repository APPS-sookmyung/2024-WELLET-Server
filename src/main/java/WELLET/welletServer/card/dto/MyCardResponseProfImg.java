package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MyCardResponseProfImg(
        @Schema(description = "프로필이미지 URL", example = "profimg/url") String profImgUrl) {

    public static MyCardResponseProfImg toCardDto(Card card) {
        return MyCardResponseProfImg.builder()
                .profImgUrl(card.getProfImgUrl())
                .build();
    }
}
