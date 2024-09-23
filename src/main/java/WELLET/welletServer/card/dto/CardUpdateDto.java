package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CardUpdateDto {

    @NotBlank
    @Schema(description = "이름", example = "수정")
    private String name;

    @NotBlank @Schema(description = "회사", example = "WELLET Corp.")
    private String company;

    @Schema(description = "직책", example = "백엔드 개발자/팀장") private String position;
    @Schema(description = "부서", example = "개발팀") private String department;
    @NotBlank @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;

    @Schema(description = "이메일", example = "ajung7038@naver.com")
    private String email;

    @Schema(description = "유선전화", example = "02-111-1111") private String tel;

    @Schema(description = "주소", example = "서울특별시 용산구 청파로 47길 100(청파동 2가)") private String address;
    @Schema(description = "메모", example = "메모") private String memo;

    public static CardUpdateDto toCardUpdateDto(Card card) {
        return CardUpdateDto.builder()
                .name(card.getName())
                .company(card.getCompany())
                .position(card.getPosition())
                .department(card.getDepartment())
                .phone(card.getPhone())
                .email(card.getEmail())
                .tel(card.getTel())
                .address(card.getAddress())
                .memo(card.getMemo())
                .build();

    }
}
