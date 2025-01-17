package WELLET.welletServer.kakaologin.security;

import WELLET.welletServer.kakaologin.jwt.JwtAuthenticationFilter;
import WELLET.welletServer.kakaologin.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration("kakaoSecurityConfig")  // 이름 충돌 방지
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    @Value("${cors.allowed.origin}")
    private String allowedOrigin;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Customizer를 사용한 CORS 설정
                .csrf(csrf -> csrf.disable()) //CSRF 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/kakao/callback").permitAll() // 카카오 로그인은 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build(); // SecurityFilterChain 빌드
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(allowedOrigin);
        configuration.addAllowedOrigin("http://localhost:8000"); // 로컬 개발 환경
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 포함 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}
