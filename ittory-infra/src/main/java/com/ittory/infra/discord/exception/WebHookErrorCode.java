package com.ittory.infra.discord.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum WebHookErrorCode {

    WEBHOOK_MESSAGE_SEN_FAIL_ERROR(BAD_REQUEST, "6000", "Discord WebHook 전송에 실패했습니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
