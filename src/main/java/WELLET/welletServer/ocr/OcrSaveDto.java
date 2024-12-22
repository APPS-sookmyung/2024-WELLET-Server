package WELLET.welletServer.ocr;

import org.springframework.web.multipart.MultipartFile;

public record OcrSaveDto(MultipartFile file) {
}
