package com.ittory.api.config.security.filter;

import static com.ittory.common.constant.TokenConstant.ACCESS_TOKEN_HEADER;

import com.ittory.common.jwt.exception.JwtException.NoAccessTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        if (request.getHeader(ACCESS_TOKEN_HEADER) == null) {
            throw new NoAccessTokenException();
        }
    }
}
