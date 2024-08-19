package WELLET.welletServer;


import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import WELLET.welletServer.files.FileUploader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Health Check", description = "API 테스트")
@RequiredArgsConstructor
public class HealthCheck {
    private final FileUploader fileUploader;
    private final String TEST_KEY = "test/";

    @GetMapping("/health")
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }

    @PostMapping("/file")
    @Operation(summary = "서버 파일 업로드 테스트용 API")
    public BasicResponse<String> healthCheckFile (@ModelAttribute MultipartFile file) {
        String url = fileUploader.uploadFile(file, TEST_KEY);
        return ResponseUtil.success(url);
    }
}
