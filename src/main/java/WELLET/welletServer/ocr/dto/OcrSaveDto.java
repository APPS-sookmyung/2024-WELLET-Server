package WELLET.welletServer.ocr.dto;
import org.springframework.web.multipart.MultipartFile;
public record OcrSaveDto(MultipartFile file) {
}
