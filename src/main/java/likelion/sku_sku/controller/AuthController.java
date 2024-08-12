package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "관리자 페이지: 구글 로그인 세션 관련")
public class AuthController {

    private final AuthService authService;
    @Operation(summary = "(민규) 구글 로그인", description = "body에 token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "bearer token 발급")})
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> requestPayload) {
        return authService.googleLogin(requestPayload);
    }
}
