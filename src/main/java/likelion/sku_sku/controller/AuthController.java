package likelion.sku_sku.controller;

import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.repository.LionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private LionRepository lionRepository;

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> requestPayload) {
        String token = requestPayload.get("token");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token verification failed");
        }

        if (idToken != null) {
            GoogleIdToken.Payload googlePayload = idToken.getPayload();
            String userId = googlePayload.getSubject();
            String email = googlePayload.getEmail();

            Optional<Lion> optionalLion = lionRepository.findByEmail(email);
            if (optionalLion.isPresent()) {
                Lion lion = optionalLion.get();
                String jwtToken = createJwtToken(userId, email, lion.getRole());
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
        }
    }

    private String createJwtToken(String userId, String email, RoleType role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 유효기간
                .signWith(SignatureAlgorithm.HS512, "askqwhrkjweagfjasdfasfdahsjkfhqlwkjfhbasdjkfhlqwkjefhbadskjfbalsdhfvbasdfasdfasdfasdfasdqmjhfvgjasdasdfasdf")
                .compact();
    }
}
