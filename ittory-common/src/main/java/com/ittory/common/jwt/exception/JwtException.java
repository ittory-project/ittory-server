package com.ittory.common.jwt.exception;

import static com.ittory.common.jwt.exception.JwtErrorCode.EXPIRED_TOKEN;
import static com.ittory.common.jwt.exception.JwtErrorCode.INVALIDATE_TOKEN;
import static com.ittory.common.jwt.exception.JwtErrorCode.NO_ACCESS_TOKEN;
import static com.ittory.common.jwt.exception.JwtErrorCode.UN_SUPPORTED_TOKEN;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class JwtException extends GlobalException {

    public JwtException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class InvalidateTokenException extends JwtException {
        public InvalidateTokenException(String token) {
            super(INVALIDATE_TOKEN.getStatus(),
                    new ErrorInfo<>(INVALIDATE_TOKEN.getCode(), INVALIDATE_TOKEN.getMessage(), token));
        }
    }

    public static class ExpiredTokenException extends JwtException {
        public ExpiredTokenException(String message) {
            super(EXPIRED_TOKEN.getStatus(),
                    new ErrorInfo<>(EXPIRED_TOKEN.getCode(), EXPIRED_TOKEN.getMessage(), message));
        }
    }

    public static class NoAccessTokenException extends JwtException {
        public NoAccessTokenException() {
            super(NO_ACCESS_TOKEN.getStatus(),
                    new ErrorInfo<>(NO_ACCESS_TOKEN.getCode(), NO_ACCESS_TOKEN.getMessage()));
        }
    }

    public static class UnSupportedTokenException extends JwtException {
        public UnSupportedTokenException(String token) {
            super(UN_SUPPORTED_TOKEN.getStatus(),
                    new ErrorInfo<>(UN_SUPPORTED_TOKEN.getCode(), UN_SUPPORTED_TOKEN.getMessage(), token));
        }
    }

}
