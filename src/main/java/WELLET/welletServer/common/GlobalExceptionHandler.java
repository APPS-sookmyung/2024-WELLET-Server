package WELLET.welletServer.common;

import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.category.exception.CategoryException;
import WELLET.welletServer.common.response.ErrorEntity;
import WELLET.welletServer.kakaologin.exception.TokenException;
import WELLET.welletServer.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity validationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        log.error("Dto Validation Exception({}): {}", "BAD_INPUT", errors);
        return new ErrorEntity("BAD_INPUT", "입력이 올바르지 않습니다.", errors);
    }

    @ExceptionHandler(CardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity cardNotFoundException(CardException e) {
        log.error("Card Not Found({})={}", e.getCode(), e.getMessage());
        return new ErrorEntity(e.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler(MemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity memberNotFoundException(MemberException e) {
        log.error("Member Not Found({})={}", e.getCode(), e.getMessage());
        return new ErrorEntity(e.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler(CategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity categoryNotFoundException(CategoryException e) {
        log.error("Category Not Found({})={}", e.getCode(), e.getMessage());
        return new ErrorEntity(e.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity InvalidTokenException(TokenException e) {
        log.error("Invalid Token Exception({})={}", e.getCode(), e.getMessage());
        return new ErrorEntity(e.getCode().toString(), e.getMessage());
    }
}
