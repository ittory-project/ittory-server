package com.ittory.common.jwt.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {

    INVALIDATE_TOKEN(ErrorStatus.UNAUTHORIZED, "3000", "잘못된 형식의 토큰입니다."),
    EXPIRED_TOKEN(ErrorStatus.UNAUTHORIZED, "3001", "만료된 토큰입니다."),
    NO_ACCESS_TOKEN(ErrorStatus.UNAUTHORIZED, "3002", "Access Token이 존재하지 않습니다."),
    UN_SUPPORTED_TOKEN(ErrorStatus.UNAUTHORIZED, "3003", "지원하지 않는 형식의 토큰입니다."),
    UNAUTHORIZED(ErrorStatus.UNAUTHORIZED, "3004", "인증에 실패했습니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
