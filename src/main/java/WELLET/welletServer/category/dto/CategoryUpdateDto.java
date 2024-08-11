package WELLET.welletServer.category.dto;

import WELLET.welletServer.category.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {

    @Schema(description = "그룹명", example = "학교")
    private  String name;

    public static CategoryUpdateDto toCategoryUpdateDto(Category category) {
        return CategoryUpdateDto.builder()
                .name(category.getName())
                .build();
    }

}
