package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MyCardResponse(
        @Schema(description = "아이디", example = "1") @NotNull Long id,
        @Schema(description = "이름", example = "주아정") @NotBlank String name,
        @Schema(description = "직책/부서", example = "백엔드 개발자/팀장") String role,
        @Schema(description = "회사", example = "WELLET Corp.") @NotBlank String company,
        @Schema(description = "휴대폰", example = "010-1111-2222") String phone,
        @Schema(description = "이메일", example = "ajung7038@naver.com") @NotBlank String email,
        @Schema(description = "유선전화", example = "02-111-1111") String tel,
        @Schema(description = "주소", example = "서울시 00동 00구") String address,
        @Schema(description = "프로필이미지 URL", example = "profimg/url") String profImgUrl,
        @Schema(description = "명함앞이미지 URL", example = "frontimg/url") String frontImgUrl,
        @Schema(description = "명함뒤이미지 URL", example = "backimg/url") String backImgUrl) {

    public static MyCardResponse toCardDto(Card card) {
        return MyCardResponse.builder()
                .id(card.getId())
                .name(card.getName())
                .role(card.getRole())
                .company(card.getCompany())
                .phone(card.getPhone())
                .email(card.getEmail())
                .tel(card.getTel())
                .address(card.getAddress())
                .profImgUrl(card.getProfImgUrl())
                .frontImgUrl(card.getFrontImgUrl())
                .backImgUrl(card.getBackImgUrl())
                .build();
    }
}
