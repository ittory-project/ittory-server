package com.ittory.api.auth.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    public ResponseCookie createResponseCookie(String cookieName, String value, long maxAge) {
        return ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie createExpiredResponseCookie(String cookieName, String value) {
        return ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .path("/")
                .maxAge(0) // 바로 만료
                .build();
    }

}
