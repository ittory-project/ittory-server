package com.ittory.api.auth.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {


    public ResponseCookie createResponseCookie(String cookieName, String value) {
        return ResponseCookie.from(cookieName, value)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7일간 유지
                .build();
    }

}
