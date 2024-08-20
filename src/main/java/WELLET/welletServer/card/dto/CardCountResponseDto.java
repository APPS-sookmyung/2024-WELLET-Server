package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CardCountResponseDto(
        @Schema(description = "총 합계", example = "1") int total,
        @Schema(description = "명함들", example = "목표") List<CardListResponse> cards) {

    public CardCountResponseDto(int total, List<CardListResponse> cards) {
        this.total = total;
        this.cards = cards;
    }
}
