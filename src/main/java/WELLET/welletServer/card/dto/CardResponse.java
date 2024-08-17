package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CardResponse(
        @Schema(description = "아이디", example = "1") @NotNull Long id,
        @Schema(description = "이름", example = "주아정") @NotBlank String name,
        @Schema(description = "직책", example = "팀장") String position,
        @Schema(description = "이메일", example = "ajung7038@naver.com") @NotBlank String email,
        @Schema(description = "휴대폰", example = "010-1111-2222") String phone,
        @Schema(description = "유선전화", example = "02-111-1111") String tel,
        @Schema(description = "부서", example = "1") String department,
        @Schema(description = "회사", example = "WELLET") @NotBlank String company,
        @Schema(description = "주소", example = "서울시 00동 00구") String address,
        @Schema(description = "메모", example = "메모") String memo,
        @Schema(description = "생성일자", example = "2024-08-03 15:51:46") @NotBlank String createdAt,
        @Schema(description = "명함 리스트") List<String> categories) {

    public static CardResponse toCardDto(Card card, List<String> categories) {
        return CardResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .position(card.getPosition())
                .email(card.getEmail())
                .phone(card.getPhone())
                .tel(card.getTel())
                .department(card.getDepartment())
                .company(card.getCompany())
                .address(card.getAddress())
                .memo(card.getMemo())
                .createdAt(card.getCreatedAt())
                .categories(categories)
                .build();
    }

    public static CardResponse toCardDto(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .position(card.getPosition())
                .email(card.getEmail())
                .phone(card.getPhone())
                .tel(card.getTel())
                .department(card.getDepartment())
                .company(card.getCompany())
                .address(card.getAddress())
                .createdAt(card.getCreatedAt())
                .build();
    }
}
