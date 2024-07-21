package com.ittory.api.member.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;
import lombok.Getter;

@Getter
public class MemberException extends GlobalException {

    public MemberException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(MemberErrorCode errorCode, Long memberId) {
            super(errorCode.getStatus(), new ErrorInfo<>(errorCode.getCode(), errorCode.getMessage(), memberId));
        }
    }
}
