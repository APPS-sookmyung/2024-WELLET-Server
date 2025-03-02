package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardListResponse(
        @Schema(description = "아이디", example = "1") @NotNull Long id,
        @Schema(description = "이름", example = "주아정") @NotBlank String name,
        @Schema(description = "직책", example = "백엔드 개발자") String position,
        @Schema(description = "부서", example = "개발팀") String department,
        @Schema(description = "회사", example = "WELLET Corp.") @NotBlank String company,
        @Schema(description = "생성일자", example = "2024-08-03 15:51:46") @NotBlank String createdAt,
        @Schema(description = "프로필이미지 URL", example = "profimg/url") String profImgUrl) {

    public static CardListResponse toCardList(Card card, CardImage cardImage) {
        return CardListResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .position(card.getPosition())
                .department(card.getDepartment())
                .company(card.getCompany())
                .createdAt(card.getCreatedAt())
                .profImgUrl(cardImage != null ? cardImage.getProf_img_url() : null)
                .build();
    }
}