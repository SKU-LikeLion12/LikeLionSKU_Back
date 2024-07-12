package likelion.sku_sku.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import likelion.sku_sku.domain.Lion;
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

    private final LionService lionService;
    private final JwtService jwtService;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    public ResponseEntity<?> googleLogin(Map<String, String> requestPayload) {
        String token = requestPayload.get("token");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
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

            Optional<Lion> optionalLion = lionService.getLionByEmail(email);
            if (optionalLion.isPresent()) {
                Lion lion = optionalLion.get();
                String jwtToken = jwtService.createJwtToken(userId, email, lion.getRole());
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
        }
    }
}
