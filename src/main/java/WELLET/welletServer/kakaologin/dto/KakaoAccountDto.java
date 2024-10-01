package WELLET.welletServer.kakaologin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoAccountDto {

    // 프로필 정보 제공 동의 여부
    @JsonProperty("profile_needs_agreement")
    public Boolean isProfileAgree;

    // 닉네임 제공 동의 여부
    @JsonProperty("profile_nickname_needs_agreement")
    public Boolean isNickNameAgree;

    // 프로필 사진 제공 동의 여부
    @JsonProperty("profile_image_needs_agreement")
    public Boolean isProfileImageAgree;

    // 사용자 프로필 정보
    @JsonProperty("profile")
    public ProfileDto profile;

    // 이름 제공 동의 여부
    @JsonProperty("name_needs_agreement")
    public Boolean isNameAgree;

    // 카카오계정 이름
    @JsonProperty("name")
    public String name;

    // 이메일 제공 동의 여부
    @JsonProperty("email_needs_agreement")
    public Boolean isEmailAgree;

    // 이메일 유효 여부
    @JsonProperty("is_email_vaild")
    public Boolean isEmailVaild;

    // 이메일 인증 여부
    @JsonProperty("is_email_verified")
    public Boolean isEmailVerified;

    // 카카오계정 대표 이메일
    @JsonProperty("email")
    public String email;

    // 연령대 제공 동의 여부
    @JsonProperty("age_range_needs_agreement")
    public Boolean isAgeAgree;

    // 연령대
    @JsonProperty("age_range")
    public String ageRange;

    // 출생 연도 제공 동의 여부
    @JsonProperty("birthyear_needs_agreement")
    public Boolean isBirthYearAgree;

    // 출생 연도 (YYYY 형식)
    @JsonProperty("birthyear")
    public String birthYear;

    // 생일 제공 동의 여부
    @JsonProperty("birthday_needs_agreement")
    public Boolean isBirthDayAgree;

    // 생일 (MMDD 형식)
    @JsonProperty("birthday")
    public String birthDay;

    // 생일 타입
    // 양력 혹은 음력
    @JsonProperty("birthday_type")
    public String birthDayType;

    // 성별 제공 동의 여부
    @JsonProperty("gender_needs_agreement")
    public Boolean isGenderAgree;

    // 성별
    @JsonProperty("gender")
    public String gender;

    // 전화번호 제공 동의 여부
    @JsonProperty("phone_number_needs_agreement")
    public Boolean isPhoneNumberAgree;

    // 전화번호
    // 국내 번호인 경우 +82 00-0000-0000 형식
    @JsonProperty("phone_number")
    public String phoneNumber;

    // CI 동의 여부
    @JsonProperty("ci_needs_agreement")
    public Boolean isCIAgree;

    // CI, 연계 정보
    @JsonProperty("ci")
    public String ci;

    // CI 발급 시각 UTC
    @JsonProperty("ci_authenticated_at")
    public Date ciCreatedAt;
}

