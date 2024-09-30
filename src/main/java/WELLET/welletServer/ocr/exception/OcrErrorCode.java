package WELLET.welletServer.ocr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OcrErrorCode {
    FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    FAILED_OCR_PROCESSING("OCR 처리에 실패하였습니다.");

    private final String message;
}
