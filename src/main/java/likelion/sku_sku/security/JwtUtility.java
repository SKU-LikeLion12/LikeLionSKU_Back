package likelion.sku_sku.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import likelion.sku_sku.domain.enums.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtility {

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.expire-length}")
    private long expireLength;

    public String createJwtToken(String email, RoleType role) {
        return Jwts.builder()
                .setSubject(email) // 주체를 email로 설정
                .claim("email", email) // 클레임에 email 포함
                .claim("role", role.name())  // 클레임에 role 포함
                .setExpiration(new Date(System.currentTimeMillis() + expireLength)) // 1일 유효기간
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact(); // jwt 직렬화 하여 압축된 문자열로 반환
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
    }

}
