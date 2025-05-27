package com.ittory.domain.member.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

import static com.ittory.domain.member.exception.MemberErrorCode.LETTER_BOX_ALREADY_STORED;
import static com.ittory.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND_ERROR;

public class MemberException extends GlobalException {

    public MemberException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(Long memberId) {
            super(MEMBER_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(MEMBER_NOT_FOUND_ERROR.getCode(), MEMBER_NOT_FOUND_ERROR.getMessage(), memberId));
        }

        public MemberNotFoundException(String loginId) {
            super(MEMBER_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(MEMBER_NOT_FOUND_ERROR.getCode(), MEMBER_NOT_FOUND_ERROR.getMessage(), loginId));
        }

    }

    public static class LetterBoxAlreadyStoredException extends MemberException {
        public LetterBoxAlreadyStoredException(Long letterId) {
            super(LETTER_BOX_ALREADY_STORED.getStatus(),
                    new ErrorInfo<>(LETTER_BOX_ALREADY_STORED.getCode(), LETTER_BOX_ALREADY_STORED.getMessage(),
                            letterId));
        }
    }

}
