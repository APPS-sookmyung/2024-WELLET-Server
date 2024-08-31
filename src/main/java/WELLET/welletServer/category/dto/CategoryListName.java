package WELLET.welletServer.category.dto;

import WELLET.welletServer.category.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record CategoryListName(
        @Schema(description = "그룹 아이디", example = "1") @NotBlank long id,
        @Schema(description = "그룹명", example = "음식점") @NotBlank String name) {

    public static CategoryListName toCategoryList(Category category) {
        return CategoryListName.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
