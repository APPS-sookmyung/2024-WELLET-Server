package WELLET.welletServer.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySaveDto {

    @NotBlank
    private String name;

    @Builder
    public CategorySaveDto(@Schema(description = "그룹명", example = "음식점") String name) {
        this.name = name;
    }
}
