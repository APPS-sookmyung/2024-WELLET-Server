package WELLET.welletServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origin}")
    private String frontendUrl;

    @Value("${cors.allowed.domain}")
    private String mainDomain;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie") // 쿠키 노출 허용
                .allowedOrigins("http://localhost:8000", frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용 HTTP 메서드
                .allowCredentials(true); // 쿠키 포함 허용
    }
}

