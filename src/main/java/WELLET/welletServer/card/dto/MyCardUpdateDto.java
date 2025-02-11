package WELLET.welletServer.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class MyCardUpdateDto {
    @Schema(description = "이름", example = "수정") private String name;
    @Schema(description = "회사", example = "WELLET") private String company;
    @Schema(description = "직책", example = "Web Engineer / 팀장") private String position;
    @Schema(description = "부서", example = "개발팀") private String department;
    @Schema(description = "휴대폰", example = "010-1111-2222") private String phone;
    @Schema(description = "이메일", example = "ajung7038@naver.com") private String email;
    @Schema(description = "유선전화", example = "02-111-1111") private String tel;
    @Schema(description = "주소", example = "서울시 00동 00구") private String address;

    @Schema(description = "프로필 이미지") private MultipartFile profImg;
}

