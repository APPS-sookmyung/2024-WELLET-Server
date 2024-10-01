package WELLET.welletServer.kakaologin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

    // 회원 번호
    @JsonProperty("id")
    private Long id;

    // 자동 연결 설정
    // true: 연결 상태, false: 연결 대기 상태
    @JsonProperty("has_signed_up")
    public Boolean hasSignedUp;

    // 서비스 연결 완료된 시각 UTC
    @JsonProperty("connected_at")
    public Date connectedAt;

    // 카카오싱크 간편 가입을 통해 로그인한 시각 UTC
    @JsonProperty("synched_at")
    public Date synchedAt;

    // 사용자 프로퍼티
    @JsonProperty("properties")
    public HashMap<String, String> properties;

    // 카카오 계정 정보
    @JsonProperty("kakao_account")
    public KakaoAccountDto kakaoAccount;

    // uuid 추가 정보
    @JsonProperty("for_partner")
    public PartnerDto partner;
}



