package WELLET.welletServer.kakaologin.jwt;

import WELLET.welletServer.kakaologin.exception.TokenErrorCode;
import WELLET.welletServer.kakaologin.exception.TokenException;
import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time:3600000}")  // 기본 만료 시간 1시간 설정
    private long expirationTime;

    // 생성자에서 expirationTime 주입
    public JwtService(@Value("${jwt.expiration-time}") long expirationTime) {
        this.expirationTime = expirationTime;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성 - 사용자 정보를 포함
    public String generateToken(Member user) {  // User 객체를 받아 사용자 정보 추가
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getKakaoId());  // 사용자 ID
        claims.put("username", user.getUsername());
        claims.put("nickname", user.getNickname());  // 사용자 닉네임
        claims.put("role", "USER");  // 기본 클레임

        // subject로 kakaoId 사용
        return generateToken(claims, String.valueOf(user.getKakaoId()));
    }

    // JWT 토큰 생성 - 클레임과 subject를 받아 토큰 생성
    private String generateToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();

        claims.put("sub", subject);  // 주체 (subject)
        claims.put("iat", new Date(now));  // 발행 시간 (issuedAt)
        claims.put("exp", new Date(now + expirationTime));  // 만료 시간 (expiration)

        return Jwts.builder()
                .claims(claims)  // 클레임을 추가
                .signWith(getSigningKey())  // 서명 알고리즘 및 서명 키
                .compact();
    }

    public String getJwtToCookie(HttpServletRequest request) {
        String jwtToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        } else {
            jwtToken = getTokenFromHeader(request);
        }
        return jwtToken;
    }

    // JWT 헤더에서 토큰을 가져오는 메소드
    public String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new TokenException(TokenErrorCode.NOT_FOUND_JWT);
    }

    // 토큰에서 사용자 ID 추출 메소드 추가
    public String getUsernameFromToken(String token) {
        try {
            System.out.println(token);
            String username = Jwts
                    .parser()
                    .verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("username", String.class);

            log.info("유저 네임을 반환");
            log.info(username);
            return username;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효하지 않은 토큰입니다.");
            throw new TokenException(TokenErrorCode.INVALID_JWT);
        }
    }

    public boolean isTokenExpired(String token){
        try {
            Date expirationDate = Jwts.parser()
                    .verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            log.info("토큰 유효기간 확인");
            return expirationDate.before(new Date());
        }
        catch(JwtException | IllegalArgumentException e){
            log.warn("유효하지 않은 토큰");
            throw new TokenException(TokenErrorCode.INVALID_JWT);
        }
    }
}



