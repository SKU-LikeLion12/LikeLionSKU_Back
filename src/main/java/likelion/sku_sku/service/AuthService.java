package likelion.sku_sku.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.enums.RoleType;
import likelion.sku_sku.repository.LionRepository;
import likelion.sku_sku.security.JwtUtility;
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
    private final JwtUtility jwtService;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance(); // JSON 처리를 위한 Jackson 라이브러리의 인스턴스

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    // 클라이언트로부터 받은 요청 데이터에서 토큰 추출
    public ResponseEntity<?> googleLogin(Map<String, String> requestPayload) {
        // 요청 데이터에서 토큰 추출
        String token = requestPayload.get("token");

        // 토큰이 없는 경우
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰이 없거나 비어 있습니다.");
        }

        // GoogleIdTokenVerifier를 사용하여 토큰 검증
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(token); // 토큰 검증
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 구글 토큰입니다.");
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace(); // 예외가 발생했을 때 예외의 스택 트레이스를 콘솔에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 검증에 실패했습니다: " + e.getMessage());
        }

        // Google 토큰에서 사용자 정보 추출
        GoogleIdToken.Payload googlePayload = idToken.getPayload();
        String email = googlePayload.getEmail();

        // 이메일 도메인 확인
        if (!email.endsWith("@sungkyul.ac.kr")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("성결대학교 구글 이메일로 로그인해주세요");
        }

        // 사용자 정보로 DB에서 사용자 검색
        Optional<Lion> optionalLion = lionRepository.findByEmail(email);
        if (optionalLion.isPresent()) {
            Lion lion = optionalLion.get();
            // 역할 확인
            if (lion.getRole() == RoleType.BABY_LION || lion.getRole() == RoleType.ADMIN_LION) {
                // JWT 생성 및 반환
                String jwtToken = jwtService.createJwtToken(lion.getName(), email, lion.getTrack(), lion.getRole());
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("접근 권한이 없는 유저입니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자를 찾을 수 없습니다.");
        }
    }
}
