package WELLET.welletServer.kakaologin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorCode {

    INVALID_JWT("유효하지 않은 토큰입니다.");

    private final String message;

}
