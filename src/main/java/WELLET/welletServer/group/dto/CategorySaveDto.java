package WELLET.welletServer.group.dto;

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
    public CategorySaveDto(String name) {
        this.name = name;
    }
}
