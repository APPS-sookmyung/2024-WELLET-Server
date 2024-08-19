package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyCardUpdateDto {
    @Schema(description = "이름", example = "수정") private String name;

    @Schema(description = "직책", example = "팀장") private String position;


    @Schema(description = "이메일", example = "ajung7038@naver.com") private String email;
    @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;
    @Schema(description = "유선전화", example = "02-111-1111") private String tel;
    @Schema(description = "부서", example = "1") private String department;

    @Schema(description = "회사", example = "WELLET") private String company;
    @Schema(description = "주소", example = "서울시 00동 00구") private String address;

//    @Schema(description = "큐알", example = "http://wellet.com") private String qr;
//    @Schema(description = "프로필 이미지", example = "http://wellet.com") private String profile_image;

    public static MyCardUpdateDto toCardUpdateDto(Card card) {
        return MyCardUpdateDto.builder()
                .name(card.getName())
                .position(card.getPosition())
                .email(card.getEmail())
                .phone(card.getPhone())
                .tel(card.getTel())
                .department(card.getDepartment())
                .company(card.getCompany())
                .address(card.getAddress())
                .build();

    }
}

