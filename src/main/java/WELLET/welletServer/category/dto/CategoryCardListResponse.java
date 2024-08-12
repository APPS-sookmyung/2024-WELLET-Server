package WELLET.welletServer.category.dto;

import WELLET.welletServer.categoryCard.domain.CategoryCard;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record CategoryCardListResponse(
        @Schema(description = "성명", example = "김은지")@NotBlank String cardName,
        @Schema(description = "소속", example = "개발팀") @NotBlank String cardCompany) {

    public static CategoryCardListResponse toCategoryList(CategoryCard categoryCard) {

        return CategoryCardListResponse.builder()
                .cardName(categoryCard.getCard().getName())
                .cardCompany(categoryCard.getCard().getCompany())
                .build();
    }
}
