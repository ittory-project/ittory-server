package com.ittory.common.exception;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;
import static com.ittory.common.exception.ErrorStatus.INTERNAL_SERVER;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorCode {

    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "0000", "내부 서버 에러입니다."),
    DB_ACCESS_ERROR(INTERNAL_SERVER, "0001", "DB 에러입니다."),
    ENUM_ERROR(BAD_REQUEST, "0002", "잘못된 Enum 값 입니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;

}
