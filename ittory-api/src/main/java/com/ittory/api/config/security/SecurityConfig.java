package com.ittory.api.config.security;

import com.ittory.api.config.security.filter.CustomAuthenticationEntryPoint;
import com.ittory.api.config.security.filter.ExceptionHandlerFilter;
import com.ittory.api.config.security.filter.JwtAuthenticationFilter;
import com.ittory.common.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.regex.Pattern;

import static com.ittory.common.constant.DBConstant.H2_PATH;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CorsConfigurationSource corsConfigurationSource;

    private static final String[] PERMIT_ALL_URIS = {
            "/swagger-ui/**", "/v3/api-docs/**", // Swagger
            "/api/auth/login/**", "/api/auth/refresh", // Login
            "/api/cover-type/all", "/api/cover-type/{coverTypeId}", // Cover-Type
            "/api/font/all", "/api/font/{fontId}", // Font,
            "/api/letter/detail/{letterId}", // 편지 상세
            "/api/guestbook/**", // GuestBook
            "/api/image/letter-cover" // PreSigned URL
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                .cors(config -> config.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/auth/login/kakao")
                        .requireCsrfProtectionMatcher(new CsrfRequireMatcher())
                        .csrfTokenRequestHandler(requestHandler)
                )
                .headers(header -> header.frameOptions(FrameOptionsConfig::sameOrigin))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(H2_PATH).permitAll()
                        .requestMatchers(PERMIT_ALL_URIS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling((config) -> config.authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Swagger-UI에선 CRSF-TOKEN 검사 비활성화
     */
    static class CsrfRequireMatcher implements RequestMatcher {
        private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

        @Override
        public boolean matches(HttpServletRequest request) {
            if (ALLOWED_METHODS.matcher(request.getMethod()).matches())
                return false;

            final String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("/swagger-ui")) {
                return false;
            }
            return true;
        }
    }
}
