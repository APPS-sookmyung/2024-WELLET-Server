package WELLET.welletServer.ocr;

import lombok.Getter;

@Getter
public class OcrException extends RuntimeException{
    private OcrErrorCode code;
    private String message;
    public OcrException(OcrErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }
}