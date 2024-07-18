package WELLET.welletServer.group.exception;

import lombok.Getter;

@Getter
public class CategoryException extends RuntimeException{
    private CategoryErrorCode code;
    private String message;

    public CategoryException(CategoryErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}
