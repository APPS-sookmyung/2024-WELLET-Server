package WELLET.welletServer.category.dto;

import WELLET.welletServer.category.domain.CategoryCard;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record CardListResponse(@NotBlank String cardName, @NotBlank String cardCompany) {

    public static CardListResponse toCategoryList(CategoryCard categoryCard) {

        return CardListResponse.builder()
                .cardName(categoryCard.getCardName())
                .cardCompany(categoryCard.getCardCompany())
                .build();
    }
}
