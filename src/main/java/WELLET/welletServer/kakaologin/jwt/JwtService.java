package WELLET.welletServer.kakaologin.jwt;

import WELLET.welletServer.member.domain.Member;
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import WELLET.welletServer.kakaologin.domain.KakaoUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

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
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

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

    // JWT 헤더에서 토큰을 가져오는 메소드
    public String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
    }

    // 토큰에서 사용자 ID 추출 메소드 추가
    public String getUsernameFromToken(String token) {
//    public String extractAllClaims(String token) {
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
//            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    // JWT에서 사용자 정보 추출
//    public String extractUserId(String token) {
//        return extractAllClaims(token).get("id", String.class);  // 클레임에서 사용자 ID 추출
//    }

    // JWT에서 모든 클레임 정보 추출
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    // JWT 만료 여부 확인
//    public boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }

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
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
            //            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }
    }
}



