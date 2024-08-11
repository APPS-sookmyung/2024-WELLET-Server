package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardSaveDto {
    // 이름, 직책, 이메일, 휴대폰, 유선 전화, 부서, 회사, 주소, 메모
    @NotBlank @Schema(description = "아이디", example = "1")
    private String name;
    @Schema(description = "이름", example = "주아정") private String position;

    @NotBlank @Schema(description = "이메일", example = "ajung7038@naver.com")
    private String email;

    @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;
    @Schema(description = "유선전화", example = "02-111-1111") private String tel;
    @Schema(description = "부서", example = "1") private String department;

    @NotBlank @Schema(description = "회사", example = "WELLET")
    private String company;

    @Schema(description = "주소", example = "서울시 00동 00구") private String address;
    @Schema(description = "메모", example = "메모") private String memo;

    @Schema(description = "생성일자", example = "[\"명함\", \"카테고리\"]")
    private List<String> categoryNames;

    @Builder
    public CardSaveDto(String name, String position, String email, String phone, String tel, String department, String company, String address, String memo, List<String> categoryNames) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.department = department;
        this.company = company;
        this.address = address;
        this.memo = memo;
        this.categoryNames = categoryNames;
    }
}
