package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardListResponse(
        @Schema(description = "아이디", example = "1") @NotNull Long id,
        @Schema(description = "이름", example = "주아정") @NotBlank String name,
        @Schema(description = "회사", example = "WELLET") @NotBlank String company,
        @Schema(description = "생성일자", example = "2024-08-03 15:51:46") @NotBlank String createdAt) {

    public static CardListResponse toCardList(Card card) {
        return CardListResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .company(card.getCompany())
                .createdAt(card.getCreatedAt())
                .build();
    }
}