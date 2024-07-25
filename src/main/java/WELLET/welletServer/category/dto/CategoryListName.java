package WELLET.welletServer.category.dto;

import WELLET.welletServer.category.domain.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record CategoryListName(@NotBlank String name) {

    public static CategoryListName fromCategory(Category category) {
        return CategoryListName.builder()
                .name(category.getName())
                .build();
    }
}
