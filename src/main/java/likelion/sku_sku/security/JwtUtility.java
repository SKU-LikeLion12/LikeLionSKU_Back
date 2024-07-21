package likelion.sku_sku.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import likelion.sku_sku.domain.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtility {

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    public String createJwtToken(String email, RoleType role) {
        return Jwts.builder()
                .setSubject(email) // 주체를 email로 설정
                .claim("email", email) // 클레임에 email 포함
                .claim("role", role.name())  // 클레임에 role 포함
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 유효기간
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact(); // jwt 직렬화 하여 압축된 문자열로 반환
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token); // 토큰을 비밀 키를 사용하여 파싱 및 검증
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody(); // jwt를 파싱하여 클레임 추출 및 반환
    }
}
