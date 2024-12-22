package WELLET.welletServer.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Value("${kakao.redirect_uri}")
    private String redirectUri;
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .allowedOrigins(redirectUri)// 프론트 서버 주소
                .allowedMethods(String.valueOf(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"))) // 허용할 HTTP method
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }
}