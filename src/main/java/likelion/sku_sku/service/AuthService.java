package likelion.sku_sku.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.repository.LionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final LionRepository lionRepository;
    private final JwtService jwtService;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    public ResponseEntity<?> googleLogin(Map<String, String> requestPayload) {
        String token = requestPayload.get("token");

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is missing or empty");
        }

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(token);
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token verification failed: " + e.getMessage());
        }

        GoogleIdToken.Payload googlePayload = idToken.getPayload();
        String email = googlePayload.getEmail();

        Optional<Lion> optionalLion = lionRepository.findByEmail(email);
        if (optionalLion.isPresent()) {
            Lion lion = optionalLion.get();
            if (email.endsWith("@sungkyul.ac.kr") && (lion.getRole() == RoleType.BABY_LION || lion.getRole() == RoleType.ADMIN_LION)) {
                String jwtToken = jwtService.createJwtToken(email, lion.getRole());
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have the required role");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }
}
