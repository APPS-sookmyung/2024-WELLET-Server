package WELLET.welletServer.login.jwt;

import WELLET.welletServer.login.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.http.HttpHeaders;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor

public class JwtService implements InitializingBean {

    private final RedisDao redisDao;
    private final JwtProperties jwtProperties;
    private long tokenValidityInMillySeconds;
    private long refreshTokenValidityInMillySeconds;
    private Key key;

    @Autowired
    RedisTemplate<String ,String> redisTemplate;
    private ValueOperations<String, String> valueOperations;

    //iwt 관련 값 setting
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShakeyFor(keyBytes);
        tokenValidityInMillySeconds = jwtProperties.getTokenValidityInMinutes() *60 *1000;
        refreshTokenValidityInMillySeconds = jwtProperties.getRefrechTokenValidityInMinutes() *60 *1000;
        valueOperations = redisTemplate.opsForValue();
    }

    // 엑세스 토큰 생성
    public String createAccessToken(String mail) {
        Date validity = new Date(System.currentTimeMillis() + tokenValidityInMillySeconds);

        return Jwts.builder()
                .setSubject(mail)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    //토큰 검증
    public void validateToken(String token) {
        if (redisDao.getValues(token) != null) {
            throw new CustomException(IS_TOKEN_LOGOUT);
        }
        try {
            Jwts.parserBuilder().setSigningkey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰이 만료되었습니다.", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 JWT 토큰입니다.", e);
        }
    }

    // 토큰에서 유저 메일 정보 추출
    public String getUserEmail(String token) {
        Claims body = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }

    //refresh 토큰 생성
    public String createRefreshToken(String mail) {
        Date validity = new Date(System.currentTimeMillis() + refreshTokenValidityInMillySeconds);
        String refreshToken = Jwts.builder()
                .setSubject(mail)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

        String redisKey = REFRESH_TOKEN + mail;
        valueOperations.set(redisKey, refreshToken);

        return refreshToken;
    }

    //refresh 토큰으로 엑세스 재발급
    public String reissueAccessToken(String refreshToken) {
        validateToken(refreshToken);
        String mail = getUserEmail(refreshToken);
        String redisKey = REFRESH_TOKEN + mail;
        if (refreshToken.equals(valueOperations.get(redisKey))) {
            return createAccessToken(mail);
        }
        return null;
    }

    // 헤더에서 토큰 추출
    public String getToken(NativeWebRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new NullPointerException("Header에 Authorization 값이 없습니다.");
        }
        return authorization.substring(7); // 'Bearer ' 이후의 토큰 값 추출
    }
}
