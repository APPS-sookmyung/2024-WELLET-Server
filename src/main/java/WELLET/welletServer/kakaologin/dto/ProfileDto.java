package WELLET.welletServer.kakaologin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDto {

    // 닉네임
    @JsonProperty("nickname")
    public String nickName;

    // 프로필 미리보기 이미지 URL
    @JsonProperty("thumbnail_image_url")
    public String thumbnailImageUrl;

    // 프로필 사진 URL
    @JsonProperty("profile_image_url")
    public String profileImageUrl;

    // 프로필 사진 URL 기본 프로필인지 여부
    // true : 기본 프로필, false : 사용자 등록
    @JsonProperty("is_default_image")
    public String isDefaultImage;

    // 닉네임이 기본 닉네임인지 여부
    // true : 기본 닉네임, false : 사용자 등록
    @JsonProperty("is_default_nickname")
    public Boolean isDefaultNickName;
}
