package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardResponse(@NotNull Long id, @NotBlank String name, String position, @NotBlank String email, String phone, String tel, String department, @NotBlank String company, String address, String memo, @NotBlank String createdAt) {

    public static CardResponse toCardDto(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .position(card.getPosition())
                .email(card.getEmail())
                .phone(card.getPhone())
                .tel(card.getTel())
                .department(card.getDepartment())
                .company(card.getCompany())
                .address(card.getAddress())
                .memo(card.getMemo())
                .createdAt(card.getCreatedAt())
                .build();
    }
}
