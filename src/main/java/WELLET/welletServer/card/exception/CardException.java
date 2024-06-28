package WELLET.welletServer.card.exception;

import lombok.Getter;

@Getter
public class CardException extends RuntimeException {
    private CardErrorCode code;
    private String message;

    public CardException(CardErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
