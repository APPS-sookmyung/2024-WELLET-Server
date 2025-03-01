package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CardContentResponse(
        @Schema(description = "아이디", example = "1") @NotNull Long id,
        @Schema(description = "이름", example = "주아정") @NotBlank String name,
        @Schema(description = "직책", example = "백엔드 개발자/팀장") String position,
        @Schema(description = "부서", example = "개발팀") String department,
        @Schema(description = "회사", example = "WELLET Corp.") @NotBlank String company,
        @Schema(description = "휴대폰", example = "010-1111-2222") String phone,
        @Schema(description = "이메일", example = "ajung7038@naver.com") @NotBlank String email,
        @Schema(description = "유선전화", example = "02-111-1111") String tel,
        @Schema(description = "주소", example = "서울시 00동 00구") String address,
        @Schema(description = "메모", example = "메모") String memo,
        @Schema(description = "그룹", example = "비즈니스") String category) {

    public static CardContentResponse toCardContentDto(Card card, String category) {
        return CardContentResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .position(card.getPosition())
                .department(card.getDepartment())
                .company(card.getCompany())
                .phone(card.getPhone())
                .email(card.getEmail())
                .tel(card.getTel())
                .address(card.getAddress())
                .memo(card.getMemo())
                .category(category)
                .build();
    }
}

