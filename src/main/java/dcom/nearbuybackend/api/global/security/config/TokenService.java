package dcom.nearbuybackend.api.global.security.config;

import dcom.nearbuybackend.api.domain.user.User;
import dcom.nearbuybackend.api.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // accessToken + refreshToken 생성
    public Token generateToken(String id, String role) {

        long accessTokenPeriod = 1000L * 60L * 10L; // 10분
        long refreshTokenPeriod = 1000L * 60L * 60L * 24L * 30L; // 30일

        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("role", role);

        Date now = new Date();
        return new Token(
                // accessToken
                Jwts.builder()
                        .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + accessTokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                // refreshToken
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact()
        );
    }

    // refreshToken을 이용한 accessToken 생성
    public Token generateOnlyAccessToken(String id, String role) {

        long accessTokenPeriod = 1000L * 60L * 10L; // 10분

        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("role", role);

        Date now = new Date();
        return Token.builder()
                .accessToken(Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + accessTokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact())
                .build();
    }

    // Token 유효성 검사
    public Boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"유효기간이 지난 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"지원하지 않은 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"빈 토큰입니다.");
        }
    }

    // Header에서 Token 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Token으로부터 아이디 추출
    public String getId(String accessToken) {
        return (String)Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().get("id");
    }

    // Token으로부터 아이디 추출 후 데이터베이스에 해당하는 User 반환
    public User getUserByToken(String accessToken) {
        return userRepository.findById(getId(accessToken)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccessToken 안의 유저가 존재하지 않습니다.")
        );
    }
}
