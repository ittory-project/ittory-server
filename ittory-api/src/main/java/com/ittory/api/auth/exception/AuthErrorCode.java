package com.ittory.api.auth.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;
import static com.ittory.common.exception.ErrorStatus.FORBIDDEN;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {

    NO_REFRESH_TOKEN(FORBIDDEN, "5000", "로그아웃된 사용자입니다."),
    REFRESH_TOKEN_NOT_MATCH(BAD_REQUEST, "5001", "Refresh Token이 일치하지 않습니다."),
    NOT_A_REFRESH_TOKEN_TYPE(BAD_REQUEST, "5002", "Refresh Token이 아닙니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
