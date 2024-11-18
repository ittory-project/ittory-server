package com.ittory.socket.common.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocketErrorCode {

    UNAUTHORIZED(ErrorStatus.BAD_REQUEST, "0000", "인증에 실패했습니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;

}
