package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MyCardUpdateDtoContent {
    @NotBlank @Schema(description = "이름", example = "수정") private String name;
    @NotBlank @Schema(description = "회사", example = "WELLET") private String company;
    @Schema(description = "직책", example = "Web Engineer / 팀장") private String position;
    @Schema(description = "부서", example = "개발팀") private String department;
    @NotBlank @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;
    @Schema(description = "이메일", example = "ajung7038@naver.com") private String email;
    @Schema(description = "유선전화", example = "02-111-1111") private String tel;
    @Schema(description = "주소", example = "서울시 00동 00구") private String address;
}

