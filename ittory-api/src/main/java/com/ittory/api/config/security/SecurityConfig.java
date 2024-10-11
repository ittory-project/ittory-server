package com.ittory.api.config.security;

import com.ittory.api.config.security.filter.CustomAuthenticationEntryPoint;
import com.ittory.api.config.security.filter.ExceptionHandlerFilter;
import com.ittory.api.config.security.filter.JwtAuthenticationFilter;
import com.ittory.common.jwt.JwtProvider;
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

import static com.ittory.common.constant.DBConstant.H2_PATH;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] PERMIT_ALL_URIS = {
            "/swagger-ui/**", "/v3/api-docs/**", // Swagger
            "/api/auth/login/**", "/api/auth/refresh", // Login
            "/api/cover-type/all", "/api/cover-type/{coverTypeId}", // Cover-Type
            "/api/cover-type/all", "/api/cover-type/{coverTypeId}", // Font
            "/api/guestbook/**", // GuestBook
            "/image/letter-cover" // PreSigned URL
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
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
}
