package WELLET.welletServer.files;

import lombok.Getter;

@Getter
public class S3IOException extends RuntimeException {

    private final String errorCode = "S3FILE_IO_EXCEPTION";
    private final String message;

    public S3IOException() {
        super();
        this.message = "S3 파일 업로드 Exception";
    }

    public S3IOException(String message) {
        super(message);
        this.message = message;
    }
}
