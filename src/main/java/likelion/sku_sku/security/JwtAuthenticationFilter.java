package likelion.sku_sku.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.sku_sku.exception.JwtValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter를 상속하여 HTTP 요청마다 한 번만 실행되는 필터임을 나타냄

    private final JwtUtility jwtUtility;

    @Override // 각 HTTP 요청마다 실행
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request); // 헤더에서 JWT 토큰 추출
            if (token != null && jwtUtility.validateToken(token)) { // 유효한 JWT 토큰 반환시
                Authentication auth = getAuthentication(token); // 인증 객체 생성
                if (auth != null) { // 유효한 인증 객체 반환시
                    SecurityContextHolder.getContext().setAuthentication(auth); // 현재 실행 중인 스레드의 보안 컴텍스트에 인증 정보를 설정하여 이후 요청이 인증된 사용자로 인식되도록 함
                }
            }
            filterChain.doFilter(request, response); // 다음 필터로 요청 전달
        } catch (JwtValidationException e) { // JWT 토큰 검증 오류
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 반환
            response.getWriter().write("Invalid JWT token: " + e.getMessage());
        } catch (JwtException e) { // JWT 토큰 유효성 오류
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request 반환
            response.getWriter().write("JWT processing error: " + e.getMessage());
        }
    }

    // 헤더에서 JWT 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // 헤더의 값이 존재하고 Bearer로 시작하는 경우
            return bearerToken.substring(7); // Bearer 토큰 다음에 오는 실제 토큰 반환
        }
        return null; // 그렇지 않으면 null 반환
    }

    // 유효한 JWT 토큰에서 인증 정보 생성
    private Authentication getAuthentication(String token) {
        Claims claims = jwtUtility.getClaimsFromToken(token); // JWT 토큰에서 클레임 추출
        String email = claims.getSubject(); // 클레임에서 JWT 토큰의 주체 추출
        String role = claims.get("role").toString(); // 클레임에서 역할 추출 후 문자열 변환

        if (email.endsWith("@sungkyul.ac.kr") && (role.equals("BABY_LION") || role.equals("ADMIN_LION"))) { // 추출한 이메일이 @sungkyul.ac.kr로 끝나고, 역할이 BABY_LION 또는 ADMIN_LION인 경우
            return new UsernamePasswordAuthenticationToken(email, "", Collections.singletonList(() -> "ROLE_" + role));
        } // UsernamePasswordAuthenticationToken 객체를 생성하여 사용자의 이메일, 비밀번호(여기서는 빈 문자열로 사용), 역할 정보를 포함한 객체 반환합니다.
        return null; // 그렇지 않으면 null 반환
    }
}
