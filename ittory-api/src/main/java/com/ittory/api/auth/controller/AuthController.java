package com.ittory.api.auth.controller;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.api.auth.dto.KaKaoLoginRequest;
import com.ittory.api.auth.dto.TokenRefreshRequest;
import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.api.auth.usecase.KaKaoLoginUseCase;
import com.ittory.api.auth.usecase.TokenRefreshUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final KaKaoLoginUseCase kaKaoLoginUseCase;
    private final TokenRefreshUseCase tokenRefreshUseCase;

    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokenResponse> loginByKaKao(@RequestBody KaKaoLoginRequest request) {
        AuthTokenResponse response = kaKaoLoginUseCase.execute(request.getCode());
        log.info("Login with {} in {}", response.getAccessToken(), LocalDateTime.now());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshMemberToken(@RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = tokenRefreshUseCase.execute(request.getAccessToken(),
                request.getRefreshToken());
        return ResponseEntity.ok().body(response);
    }

}
