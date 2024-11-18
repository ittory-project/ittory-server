package com.ittory.api.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRefreshRequest {

    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;

}