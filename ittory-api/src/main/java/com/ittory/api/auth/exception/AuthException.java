package com.ittory.api.auth.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

import static com.ittory.api.auth.exception.AuthErrorCode.*;

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

    public static class NotARefreshTokenTypeException extends AuthException {
        public NotARefreshTokenTypeException(String refreshToken) {
            super(NOT_A_REFRESH_TOKEN_TYPE.getStatus(),
                    new ErrorInfo<>(NOT_A_REFRESH_TOKEN_TYPE.getCode(), NOT_A_REFRESH_TOKEN_TYPE.getMessage(),
                            refreshToken));
        }
    }

}
