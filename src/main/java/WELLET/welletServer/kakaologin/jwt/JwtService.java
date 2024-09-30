package WELLET.welletServer.kakaologin.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time:3600000}")  // 기본 만료 시간 1시간 설정
    private long expirationTime;

    // 생성자에서 expirationTime 주입
    public JwtService(@Value("${jwt.expiration-time}") long expirationTime) {
        this.expirationTime = expirationTime;
    }

    // JWT 토큰 생성
    public String generateToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();

        // 클레임에 모든 정보를 넣음
        claims.put("sub", subject);  // 주체 (subject)
        claims.put("iat", new Date(now));  // 발행 시간 (issuedAt)
        claims.put("exp", new Date(now + expirationTime));  // 만료 시간 (expiration)

        return Jwts.builder()
                .addClaims(claims)  // 클레임을 직접 추가
                .signWith(SignatureAlgorithm.HS512, secretKey)  // 서명 알고리즘 및 서명 키
                .compact();
    }

    // 사용자 정보로부터 JWT 생성
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");  // 기본 클레임
        return generateToken(claims, userId);
    }

    // JWT에서 사용자 정보 추출
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    // JWT에서 모든 클레임 정보 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}


