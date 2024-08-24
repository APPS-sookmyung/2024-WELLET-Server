package WELLET.welletServer.category.dto;

import WELLET.welletServer.categoryCard.domain.CategoryCard;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record CategoryCardListResponse(
        @Schema(description = "아이디", example = "1") @NotNull Long id,
        @Schema(description = "이름", example = "주아정") @NotBlank String name,
        @Schema(description = "직책/부서", example = "백엔드 개발자") String role,
        @Schema(description = "회사", example = "WELLET Corp.") @NotBlank String company,
        @Schema(description = "생성일자", example = "2024-08-03 15:51:46") @NotBlank String createdAt) {

    public static CategoryCardListResponse toCategoryList(CategoryCard categoryCard) {

        return CategoryCardListResponse.builder()
                .id(categoryCard.getCard().getId())
                .name(categoryCard.getCard().getName())
                .role(categoryCard.getCard().getRole())
                .company(categoryCard.getCard().getCompany())
                .createdAt(categoryCard.getCard().getCreatedAt())
                .build();
    }
}
