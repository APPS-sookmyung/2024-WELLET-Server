package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CardListResponse(@NotBlank String name, @NotBlank String company) {

    public static CardListResponse toCardList(Card card) {
        return CardListResponse.builder()
                .name(card.getName())
                .company(card.getCompany())
                .build();
    }
}
