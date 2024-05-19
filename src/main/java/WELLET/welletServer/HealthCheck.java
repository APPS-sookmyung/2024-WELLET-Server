package WELLET.welletServer;


import WELLET.welletServer.common.response.BasicResponse;
import WELLET.welletServer.common.response.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthCheck {

    @GetMapping("/health")
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }
}
