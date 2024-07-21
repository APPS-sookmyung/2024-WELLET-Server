package WELLET.welletServer.group.dto;

import WELLET.welletServer.group.domain.Category;
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
