package com.ittory.infra.discord.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

import static com.ittory.infra.oauth.exception.OAuthErrorCode.WEBHOOK_MESSAGE_SEN_FAIL_ERROR;

public class WebHookException extends GlobalException {

    public WebHookException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class WebHookMessageSendFailException extends WebHookException {
        public WebHookMessageSendFailException() {
            super(WEBHOOK_MESSAGE_SEN_FAIL_ERROR.getStatus(),
                    new ErrorInfo<>(WEBHOOK_MESSAGE_SEN_FAIL_ERROR.getCode(), WEBHOOK_MESSAGE_SEN_FAIL_ERROR.getMessage()));
        }
    }
}
