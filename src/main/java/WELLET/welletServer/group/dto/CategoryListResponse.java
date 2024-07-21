package WELLET.welletServer.group.dto;

import WELLET.welletServer.group.domain.Category;
import WELLET.welletServer.group.domain.CategoryCard;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;


@Builder
public record CategoryListResponse(@NotBlank String cardName, @NotBlank String cardCompany) {

    public static CategoryListResponse toCategoryList(CategoryCard categoryCard) {

        return CategoryListResponse.builder()
                .cardName(categoryCard.getCardName())
                .cardCompany(categoryCard.getCardCompany())
                .build();
    }
}
