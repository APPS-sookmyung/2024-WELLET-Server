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
    @NotBlank @Schema(description = "이름", example = "주아정")
    private String name;

    @NotBlank @Schema(description = "회사", example = "WELLET Corp.")
    private String company;

    @Schema(description = "직책/부서", example = "백엔드 개발자/팀장") private String role;
    @NotBlank @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;

    @Schema(description = "이메일", example = "ajung7038@naver.com")
    private String email;

    @Schema(description = "유선전화", example = "02-111-1111") private String tel;

    @Schema(description = "주소", example = "서울특별시 용산구 청파로 47길 100(청파동 2가)") private String address;
    @Schema(description = "메모", example = "메모") private String memo;

    @Schema(description = "그룹", example = "[\"비즈니스\"]")
    private List<String> categoryNames;

    @Builder
    public CardSaveDto(String name, String company, String role, String phone, String email, String tel, String address, String memo, List<String> categoryNames) {
        this.name = name;
        this.company = company;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.memo = memo;
        this.categoryNames = categoryNames;
    }
}
