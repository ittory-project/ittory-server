package com.ittory.api.auth.controller;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.api.auth.dto.KaKaoLoginRequest;
import com.ittory.api.auth.dto.TokenRefreshRequest;
import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.api.auth.usecase.KaKaoLoginUseCase;
import com.ittory.api.auth.usecase.LogoutUseCase;
import com.ittory.api.auth.usecase.TokenRefreshUseCase;
import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final KaKaoLoginUseCase kaKaoLoginUseCase;
    private final TokenRefreshUseCase tokenRefreshUseCase;
    private final LogoutUseCase logoutUseCase;

    @Operation(summary = "카카오 소셜 로그인", description = "카카오 AccessToken 필요.")
    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokenResponse> loginByKaKao(@Valid @RequestBody KaKaoLoginRequest request) {
        AuthTokenResponse response = kaKaoLoginUseCase.execute(request.getAccessToken());
        log.info("Login with {} in {}", response.getAccessToken(), LocalDateTime.now());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Access Token 갱신", description = "잇토리 AccessToken, 잇토리 RefreshToken 필요.")
    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshMemberToken(@Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = tokenRefreshUseCase.execute(request.getAccessToken(),
                request.getRefreshToken());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "로그아웃", description = "(Authenticated) RefreshToken 삭제.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CurrentMemberId Long memberId) {
        logoutUseCase.execute(memberId);
        return ResponseEntity.ok().build();
    }

}
