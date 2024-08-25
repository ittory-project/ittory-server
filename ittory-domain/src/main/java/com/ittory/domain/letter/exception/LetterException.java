package com.ittory.domain.letter.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class LetterException extends GlobalException {

    public LetterException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class LetterNotFoundException extends LetterException {
        public LetterNotFoundException(Long letterId) {
            super(LetterErrorCode.LETTER_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(LetterErrorCode.LETTER_NOT_FOUND_ERROR.getCode(),
                            LetterErrorCode.LETTER_NOT_FOUND_ERROR.getMessage(), letterId));
        }
    }
}
