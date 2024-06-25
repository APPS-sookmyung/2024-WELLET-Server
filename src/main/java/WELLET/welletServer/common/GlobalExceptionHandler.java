package WELLET.welletServer.common;

import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ErrorEntity;
import WELLET.welletServer.common.response.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<ErrorEntity> cardNotFoundException(CardException e) {
        log.error("Card Not Found({})={}", e.getCode(), e.getMessage());
        return ResponseUtil.error(new ErrorEntity(e.getCode().toString(), e.getMessage()));
    }
}
