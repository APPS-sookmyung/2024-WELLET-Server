package WELLET.welletServer.category.dto;

import WELLET.welletServer.card.dto.CardListResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoryCountResponse (
        @Schema(description = "총 합계", example = "1") int total,
        @Schema(description = "명함들", example = "목표") List<CategoryCardListResponse> cards) {

    public CategoryCountResponse(int total, List<CategoryCardListResponse> cards) {
        this.total = total;
        this.cards = cards;
    }
}
