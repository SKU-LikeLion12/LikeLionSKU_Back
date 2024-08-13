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

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.expire-length}")
    private long expireLength;

    public String createJwtToken(String name, String email, TrackType track, RoleType role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("track", track.name())
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + expireLength))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            // JWT 서명 실패
            throw new JwtValidationException("SignatureException", e);
        } catch (MalformedJwtException e) {
            // 잘못된 형식의 JWT
            throw new JwtValidationException("MalformedJwtException", e);
        } catch (ExpiredJwtException e) {
            // 만료된 JWT
            throw new JwtValidationException("ExpiredJwtException", e);
        } catch (UnsupportedJwtException e) {
            // 지원하지 않는 JWT
            throw new JwtValidationException("UnsupportedJwtException", e);
        } catch (IllegalArgumentException e) {
            // JWT claims이 비어 있음
            throw new JwtValidationException("IllegalArgumentException", e);
        }
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Expired JWT token", e);
        } catch (JwtException e) {
            throw new JwtValidationException("Invalid JWT token", e);
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

}
