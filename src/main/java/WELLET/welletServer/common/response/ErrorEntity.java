package WELLET.welletServer.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorEntity {
    private String errorCode;
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> errors;

    public ErrorEntity(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = new HashMap<>();
    }
}
