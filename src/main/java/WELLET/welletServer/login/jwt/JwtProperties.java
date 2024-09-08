package WELLET.welletServer.login.jwt;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String header;
    private String secret;
    private long tokenValidityInMinutes;
    private long refreshTokenValidityInMinutes;

    @PostConstruct
    private void validateProperties() {
        if (tokenValidityInMinutes <= 0) {
            throw new IllegalArgumentException("Token validity must be greater than 0.");
        }
        if (refreshTokenValidityInMinutes <= 0) {
            throw new IllegalArgumentException("Refresh token validity must be greater than 0.");
        }
    }
}
