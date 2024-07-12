package likelion.sku_sku.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import likelion.sku_sku.domain.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    public String createJwtToken(String userId, String email, RoleType role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("role", role.name())  // role을 문자열로 변환
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 유효기간
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }
}
