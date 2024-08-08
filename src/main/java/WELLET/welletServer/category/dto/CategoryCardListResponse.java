package WELLET.welletServer.category.dto;

import WELLET.welletServer.categoryCard.domain.CategoryCard;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record CategoryCardListResponse(@NotBlank String cardName, @NotBlank String cardCompany) {

    public static CategoryCardListResponse toCategoryList(CategoryCard categoryCard) {

        return CategoryCardListResponse.builder()
                .cardName(categoryCard.getCategory().getName())
                .cardCompany(categoryCard.getCard().getCompany())
                .build();
    }
}
