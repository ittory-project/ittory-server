package com.ittory.api.auth.exception;

import static com.ittory.api.auth.exception.AuthErrorCode.NO_REFRESH_TOKEN;
import static com.ittory.api.auth.exception.AuthErrorCode.REFRESH_TOKEN_NOT_MATCH;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class AuthException extends GlobalException {

    public AuthException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class NoRefreshTokenException extends AuthException {
        public NoRefreshTokenException() {
            super(NO_REFRESH_TOKEN.getStatus(),
                    new ErrorInfo<>(NO_REFRESH_TOKEN.getCode(), NO_REFRESH_TOKEN.getMessage()));
        }
    }

    public static class RefreshTokenNotMatchException extends AuthException {
        public RefreshTokenNotMatchException(String refreshToken) {
            super(REFRESH_TOKEN_NOT_MATCH.getStatus(),
                    new ErrorInfo<>(REFRESH_TOKEN_NOT_MATCH.getCode(), REFRESH_TOKEN_NOT_MATCH.getMessage(),
                            refreshToken));
        }
    }

}
