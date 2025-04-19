package com.ittory.api.auth.controller;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.api.auth.dto.IdLoginRequest;
import com.ittory.api.auth.service.IdLoginService;
import com.ittory.api.auth.util.CookieProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Profile("dev")
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthTestController {

    private final IdLoginService idLoginService;

    private final CookieProvider cookieProvider;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;

    @Operation(summary = "아이디로 로그인", description = "테스트를 위한 로그인")
    @PostMapping("/login/id")
    public ResponseEntity<AuthTokenResponse> loginByKaKao(@Valid @RequestBody IdLoginRequest request,
                                                          HttpServletResponse response) {
        AuthTokenResponse tokenResponse = idLoginService.login(request.getLoginId());

        log.info("Login with {} in {}", tokenResponse.getAccessToken(), LocalDateTime.now());
        ResponseCookie refreshTokenCookie = cookieProvider.createResponseCookie(REFRESH_TOKEN_COOKIE_NAME, tokenResponse.getRefreshToken(), REFRESH_TOKEN_EXPIRATION_TIME);
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok().body(tokenResponse);
    }
}
