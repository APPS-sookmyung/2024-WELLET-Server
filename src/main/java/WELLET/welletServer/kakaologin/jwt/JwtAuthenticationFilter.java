package WELLET.welletServer.kakaologin.jwt;

import WELLET.welletServer.kakaologin.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. 헤더에서 JWT 추출
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }

        // 2. JWT가 존재하고 유효한지 확인
        if (jwtToken != null && !jwtService.isTokenExpired(jwtToken)) {
//            String userId = jwtService.extractUserId(jwtToken);
            jwtService.isTokenExpired(jwtToken);

            // 여기서 사용자 인증 로직 추가 (ex: SecurityContextHolder에 유저 정보 추가)
        }

        // 3. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
