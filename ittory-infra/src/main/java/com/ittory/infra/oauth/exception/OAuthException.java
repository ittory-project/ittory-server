package com.ittory.infra.oauth.exception;

import static com.ittory.infra.oauth.exception.OAuthErrorCode.OAUTH_SERVER_ERROR;
import static com.ittory.infra.oauth.exception.OAuthErrorCode.SOCIAL_MEMBER_NO_INFO_ERROR;
import static com.ittory.infra.oauth.exception.OAuthErrorCode.UNAUTHORIZED_TOKEN_ERROR;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class OAuthException extends GlobalException {

    public OAuthException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class UnauthorizedTokenException extends OAuthException {
        public UnauthorizedTokenException(String token) {
            super(UNAUTHORIZED_TOKEN_ERROR.getStatus(),
                    new ErrorInfo<>(UNAUTHORIZED_TOKEN_ERROR.getCode(), UNAUTHORIZED_TOKEN_ERROR.getMessage(), token));
        }
    }

    public static class OAuthServerException extends OAuthException {
        public OAuthServerException(String url) {
            super(OAUTH_SERVER_ERROR.getStatus(),
                    new ErrorInfo<>(OAUTH_SERVER_ERROR.getCode(), OAUTH_SERVER_ERROR.getMessage(), url));
        }
    }

    public static class SocialMemberNoInfoException extends OAuthException {
        public SocialMemberNoInfoException() {
            super(SOCIAL_MEMBER_NO_INFO_ERROR.getStatus(),
                    new ErrorInfo<>(SOCIAL_MEMBER_NO_INFO_ERROR.getCode(), SOCIAL_MEMBER_NO_INFO_ERROR.getMessage()));
        }
    }
}
