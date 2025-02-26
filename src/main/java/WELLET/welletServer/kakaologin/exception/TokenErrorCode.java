package WELLET.welletServer.kakaologin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorCode {

    INVALID_JWT("유효하지 않은 토큰입니다."),
    NOT_FOUND_JWT("토큰을 찾을 수 없습니다.");

    private final String message;

}
