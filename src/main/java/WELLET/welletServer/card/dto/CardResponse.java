package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CardResponse(@NotBlank String name, String position, @NotBlank String email, String phone, String tel, String department, @NotBlank String company, String address, String memo) {

    public static CardResponse toCardDto(Card card) {
        return CardResponse.builder()
                .name(card.getName())
                .position(card.getPosition())
                .email(card.getEmail())
                .phone(card.getPhone())
                .tel(card.getTel())
                .department(card.getDepartment())
                .company(card.getCompany())
                .address(card.getAddress())
                .memo(card.getMemo())
                .build();
    }
}
