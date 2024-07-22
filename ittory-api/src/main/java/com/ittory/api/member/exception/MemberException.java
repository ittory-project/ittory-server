package com.ittory.api.member.exception;

import static com.ittory.api.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class MemberException extends GlobalException {

    public MemberException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(Long memberId) {
            super(MEMBER_NOT_FOUND.getStatus(),
                    new ErrorInfo<>(MEMBER_NOT_FOUND.getCode(), MEMBER_NOT_FOUND.getMessage(), memberId));
        }
    }
}
