package com.ittory.infra.oauth.exception;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;
import static com.ittory.common.exception.ErrorStatus.INTERNAL_SERVER;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuthErrorCode {

    UNAUTHORIZED_TOKEN_ERROR(BAD_REQUEST, "4000", "인증되지 않은 토큰입니다."),
    OAUTH_SERVER_ERROR(INTERNAL_SERVER, "4001", "OAuth 인증서버 에러입니다."),
    SOCIAL_MEMBER_NO_INFO_ERROR(BAD_REQUEST, "4002", "사용자의 소셜 정보가 없습니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
