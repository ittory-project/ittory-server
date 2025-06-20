package com.ittory.api.auth.controller;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.api.auth.dto.KaKaoLoginRequest;
import com.ittory.api.auth.dto.TokenRefreshRequest;
import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.api.auth.service.AuthService;
import com.ittory.api.auth.service.KaKaoLoginService;
import com.ittory.api.auth.util.CookieProvider;
import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final KaKaoLoginService kaKaoLoginService;
    private final AuthService authService;

    private final CookieProvider cookieProvider;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;


    @Operation(summary = "카카오 소셜 로그인", description = "카카오 AccessToken 필요.")
    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokenResponse> loginByKaKao(@Valid @RequestBody KaKaoLoginRequest request,
                                                          HttpServletRequest httpRequest,
                                                          HttpServletResponse response) {
        String origin = httpRequest.getHeader("Origin");
        // TODO: 프론트 수정 후 다음 로직으로 변경할 예정 - by junker 25.03.19.
//        AuthTokenResponse tokenResponse = kaKaoLoginService.loginOrRegister(request.getAuthorizationCode());
        AuthTokenResponse tokenResponse;
        if (request.getAccessToken() != null) {
            tokenResponse = kaKaoLoginService.loginOrRegister(request.getAccessToken(), origin, false);
        } else {
            tokenResponse = kaKaoLoginService.loginOrRegister(request.getAuthorizationCode(), origin, true);
        }


        log.info("Login with {} in {}", tokenResponse.getAccessToken(), LocalDateTime.now());
        ResponseCookie refreshTokenCookie = cookieProvider.createResponseCookie(REFRESH_TOKEN_COOKIE_NAME, tokenResponse.getRefreshToken(), REFRESH_TOKEN_EXPIRATION_TIME);
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        // TODO: 프론트 수정 후 Response Body에서 RefreshToken 삭제 - by junker 25.03.12.
        return ResponseEntity.ok().body(tokenResponse);
    }

    @Operation(summary = "Access Token 갱신", description = "잇토리 AccessToken, 잇토리 RefreshToken 필요.")
    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshMemberToken(@CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken,
                                                                   @Nullable @RequestBody TokenRefreshRequest request) {

        // TODO: 프론트 수정 후 삭제 로직 - by junker 25.03.12.
        if (refreshToken == null) {
            refreshToken = request.getRefreshToken();
        }
        TokenRefreshResponse tokenResponse = authService.renewToken(refreshToken);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @Operation(summary = "로그아웃", description = "(Authenticated) RefreshToken 삭제.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CurrentMemberId Long memberId) {
        authService.logout(memberId);
        return ResponseEntity.ok().build();
    }

}
