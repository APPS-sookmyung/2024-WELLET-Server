package WELLET.welletServer.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardSaveDto {
    // 이름, 직책, 이메일, 휴대폰, 유선 전화, 부서, 회사, 주소, 메모
    @NotBlank
    private String name;
    private String position;

    @NotBlank
    private String email;
    private String phone;
    private String tel;
    private String department;

    @NotBlank
    private String company;
    private String address;
    private String memo;

    @Builder
    public CardSaveDto(String name, String position, String email, String phone, String tel, String department, String company, String address, String memo) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.department = department;
        this.company = company;
        this.address = address;
        this.memo = memo;
    }
}
