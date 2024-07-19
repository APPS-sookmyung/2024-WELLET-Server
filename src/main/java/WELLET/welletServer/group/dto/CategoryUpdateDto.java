package WELLET.welletServer.group.dto;

import WELLET.welletServer.group.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {

    private  String name;

    public static CategoryUpdateDto toCategoryUpdateDto(Category category) {
        return CategoryUpdateDto.builder()
                .name(category.getName())
                .build();
    }

}
