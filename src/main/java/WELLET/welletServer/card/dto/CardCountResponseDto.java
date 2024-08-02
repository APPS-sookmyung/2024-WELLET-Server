package WELLET.welletServer.card.dto;

import java.util.List;

public record CardCountResponseDto(Long total, List<CardListResponse> cards) {

    public CardCountResponseDto(Long total, List<CardListResponse> cards) {
        this.total = total;
        this.cards = cards;
    }
}
