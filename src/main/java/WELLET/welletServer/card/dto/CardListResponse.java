package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardListResponse(@NotNull Long id, @NotBlank String name, @NotBlank String company, @NotBlank String createdAt) {

    public static CardListResponse toCardList(Card card) {
        return CardListResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .company(card.getCompany())
                .createdAt(card.getCreatedAt())
                .build();
    }
}