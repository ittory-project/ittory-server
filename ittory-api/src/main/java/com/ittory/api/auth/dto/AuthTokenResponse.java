package com.ittory.api.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenResponse {

    private String accessToken;
    private String refreshToken;

    public static AuthTokenResponse of(String accessToken, String refreshToken) {
        return new AuthTokenResponse(accessToken, refreshToken);
    }

}
