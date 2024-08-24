package likelion.sku_sku.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // CORS 설정이 애플리케이션의 모든 URL 경로에 적용됨
                        .allowedOrigins("http://localhost:3000", "https://sku-sku.com") // 크로스 도메인 요청을 할 수 있는 url
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // CORS 요청에 대해 허용되는 HTTP 메서드
                        .allowedHeaders("*") // 모든 헤더가 요청에 포함될 수 있음
                        .allowCredentials(true); // 자격 증명이 크로스 도메인 요청에서 전송될 수 있도록 허용
            }
        };
    }
}
