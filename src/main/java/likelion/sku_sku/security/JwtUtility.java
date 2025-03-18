package likelion.sku_sku.security;

import io.jsonwebtoken.*;
import likelion.sku_sku.domain.enums.RoleType;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.JwtValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtility {

    @Value("${jwt.base64Secret}")
    private String jwtKey; // JWT 토큰 서명에 사용할 비밀 키

//    @Value("${jwt.expire-length}")
//    private long expireLength; // JWT 토큰 만료 시간
    private static final long expireLength = 1000 * 60 * 60; // 밀리초 단위 // JWT 만료 시간: 1시간


    // JWT 토큰 생성
    public String createJwtToken(String name, String email, TrackType track, RoleType role) {
        return Jwts.builder() // JWT 빌더 초기화
                .setSubject(email) // 이메일을 JWT 토큰의 주체로 설정
                .claim("name", name) // JWT 토큰에 이름 추가
                .claim("track", track.name()) // JWT 토큰에 트랙 추가
                .claim("role", role.name()) // JWT 토큰에 역할 추가
                .setExpiration(new Date(System.currentTimeMillis() + expireLength)) // JWT 토큰의 만료시간 설정
                .signWith(SignatureAlgorithm.HS512, jwtKey) // 지정된 알고리즘과 비밀키를 사용하여 JWT 토큰 서명
                .compact(); // JWT 문자열 생성
    }

    // JWT 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token); // 주어진 JWT 토큰 파싱하여 서명을 검증
            return true; // 올바르면 true 반환
        } catch (SignatureException e) {
            // JWT 토큰 잘못된 서명
            throw new JwtValidationException("SignatureException", e);
        } catch (MalformedJwtException e) {
            // 잘못된 형식의 JWT 토큰
            throw new JwtValidationException("MalformedJwtException", e);
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 토큰
            throw new JwtValidationException("ExpiredJwtException", e);
        } catch (UnsupportedJwtException e) {
            // 지원하지 않는 JWT 토큰
            throw new JwtValidationException("UnsupportedJwtException", e);
        } catch (IllegalArgumentException e) {
            // JWT 토큰 claims이 비어 있음
            throw new JwtValidationException("IllegalArgumentException", e);
        }
    }

    // JWT 토큰에서 클레임을 추출하여 반환
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody(); // 토큰을 파싱하여 클레임을 추출
        } catch (ExpiredJwtException e) { // JWT 토큰 유효기간 만료
            throw new JwtValidationException("Expired JWT token", e);
        } catch (JwtException e) { // 유효하지 않은 JWT 토큰
            throw new JwtValidationException("Invalid JWT token", e);
        }
    }

    // JWT 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token); // JWT 토큰에서 클레임 추출
        return claims.getSubject(); // 클레임에서 토큰의 주체 추출하여 반환
    }
}
