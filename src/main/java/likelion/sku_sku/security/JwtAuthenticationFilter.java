package likelion.sku_sku.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.sku_sku.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    @Override                        // 클라이언트 요청 객체       // 서버 응답 객체               // 필터 체인을 통해 요청을 다음 필터로 전달
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = resolveToken(request); // 요청에서 jwt 추출
            if (token != null && jwtUtility.validateToken(token)) { // jwt가 존재하고 유효한 경우
                Authentication auth = getAuthentication(token); // jwt에서 인증 객체 생성
                if (auth != null) { // 인증 객체가 존재하면
                    SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContext에 인증 객체 설정
                }
            }
            filterChain.doFilter(request, response); // 다음 필터 또는 요청 처리로 진행
        } catch (InvalidTokenException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }

    private String resolveToken(HttpServletRequest request) throws InvalidTokenException {
        String bearerToken = request.getHeader("Authorization"); // Authorization Headers에서 token 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Bearer token인지 확인
            return bearerToken.substring(7); // "Bearer " 부분을 제외한 token 반환
        }
        throw new InvalidTokenException("유효하지 않은 Authorization Headers입니다.");
    }

    private Authentication getAuthentication(String token) throws InvalidTokenException {
        Claims claims = jwtUtility.getClaimsFromToken(token); // jwt에서 claims 추출
        String email = claims.get("email").toString(); // claims에서 email 추출
        String role = claims.get("role").toString(); // claims에서 role 추출

        if (email.endsWith("@sungkyul.ac.kr") && (role.equals("BABY_LION") || role.equals("ADMIN_LION"))) { // 조건 확인
            return new UsernamePasswordAuthenticationToken(email, "", Collections.singletonList(() -> "ROLE_" + role)); // 인증 객체 생성
        }
        throw new InvalidTokenException("유효하지 않은 token claims입니다.");
    }
}
