package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyCardUpdateDto {
    @Schema(description = "이름", example = "수정") private String name;
    @Schema(description = "회사", example = "WELLET") private String company;
    @Schema(description = "직책/부서", example = "Web Engineer / 팀장") private String role;
    @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;
    @Schema(description = "이메일", example = "ajung7038@naver.com") private String email;
    @Schema(description = "유선전화", example = "02-111-1111") private String tel;
    @Schema(description = "주소", example = "서울시 00동 00구") private String address;

    public static MyCardUpdateDto toCardUpdateDto(Card card) {
        return MyCardUpdateDto.builder()
                .name(card.getName())
                .company(card.getCompany())
                .role(card.getRole())
                .phone(card.getPhone())
                .email(card.getEmail())
                .tel(card.getTel())
                .address(card.getAddress())
                .build();

    }
}

