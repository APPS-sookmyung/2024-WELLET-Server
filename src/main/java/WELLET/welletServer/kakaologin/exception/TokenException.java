package WELLET.welletServer.kakaologin.exception;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
    private TokenErrorCode code;
    private String message;

    public TokenException(TokenErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }

    public TokenException(TokenErrorCode code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
