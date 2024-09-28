package likelion.sku_sku.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtility jwtUtility;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable) // spring security 기본 인증 해제 -> JWT 사용
                .csrf(csrf -> csrf.disable()) // CSRF  비활성화 -> JWT 토큰을 사용한 인증에서는 비활성화하는 경우가 많음
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger UI와 API 문서화 경로에 대한 접근을 모든 사용자에게 허용
                                .requestMatchers("/api/auth/**").permitAll() // 인증 관련 API 경로에 대한 접근을 모든 사용자에게 허용
                                .requestMatchers("/admin/project/all").permitAll() // 해당 url에 대한 접근도 모든 사용자에게 허용
                                .requestMatchers("/admin/**").hasRole("ADMIN_LION") // 해당 url에 대한 접근은 해당 role을 가지고 있어야 함
                                .requestMatchers("/assignment/**", "/lecture/**", "/submit/**").hasAnyRole("ADMIN_LION", "BABY_LION", "LEGACY_LION") // 해당 url에 대한 접근은 해당 role을 가지고 있어야 함
                                .anyRequest().permitAll() // 이외의 모든 요청에 대해 접근을 허용
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtility), UsernamePasswordAuthenticationFilter.class); // 각 요청에 대해 JWT 토큰을 검증하고, 유효한 토큰이면 인증 정보를 설정

        return http.build(); // 설정이 완료된 HttpSecurity 객체를 빌드하여 SecurityFilterChain을 반환
    }
}
