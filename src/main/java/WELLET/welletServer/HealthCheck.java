package WELLET.welletServer;


import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Tag(name = "Health Check", description = "API 테스트")
public class HealthCheck {

    @GetMapping("/health")
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }
}
