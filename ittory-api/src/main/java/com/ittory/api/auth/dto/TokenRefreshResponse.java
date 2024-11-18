package com.ittory.api.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRefreshResponse {

    private String accessToken;

    public static TokenRefreshResponse of(String accessToken) {
        return new TokenRefreshResponse(accessToken);
    }

}