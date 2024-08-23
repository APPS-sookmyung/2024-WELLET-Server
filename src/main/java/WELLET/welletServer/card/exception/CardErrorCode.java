package WELLET.welletServer.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardErrorCode {
    CARD_NOT_FOUND("명함을 찾을 수 없습니다."),
    DUPLICATE_MY_CARD("내 명함이 이미 존재합니다.");

    private final String message;
}
