package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCardSaveDto  {
    @NotBlank @Schema(description = "이름", example = "주아정")
    private String name;

    @Schema(description = "회사", example = "WELLET Corp.") private String company;
    @Schema(description = "직책/부서", example = "Web Engineer / 개발팀") private String role;
    @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;

    @NotBlank @Schema(description = "이메일", example = "ajung7038@naver.com")
    private String email;

    @Schema(description = "유선전화", example = "02-111-1111") private String tel;
    @Schema(description = "주소", example = "서울시 00동 00구") private String address;
    @Schema(description = "프로필 이미지파일") private MultipartFile profImg;
    @Schema(description = "명함앞 이미지파일") private MultipartFile frontImg;
    @Schema(description = "명함뒤 이미지파일") private MultipartFile backImg;
    @Schema(description = "프로필 이미지URL", example = "https://bucket-name.s3.amazonaws.com") @Setter private String profImgUrl;
    @Schema(description = "명함앞 이미지URL", example = "https://bucket-name.s3.amazonaws.com") @Setter private String frontImgUrl;
    @Schema(description = "명함뒤 이미지URL", example = "https://bucket-name.s3.amazonaws.com") @Setter private String backImgUrl;


    @Builder
    public MyCardSaveDto(String name, String company, String role, String phone, String email, String tel, String address, MultipartFile profImg, MultipartFile frontImg, MultipartFile backImg) {
        this.name = name;
        this.company = company;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.profImg = profImg;
        this.frontImg = frontImg;
        this.backImg = backImg;
    }
}
