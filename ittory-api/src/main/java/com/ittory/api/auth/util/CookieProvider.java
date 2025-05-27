package com.ittory.api.auth.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    public ResponseCookie createResponseCookie(String cookieName, String value, long maxAge) {
        return ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true)
                .maxAge(maxAge)
                .build();
    }

}
