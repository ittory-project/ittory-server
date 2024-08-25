package com.ittory.domain.letter.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class LetterElementException extends GlobalException {

    public LetterElementException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class LetterElementNotFoundException extends LetterElementException {
        public LetterElementNotFoundException(Long letterElementId) {
            super(LetterElementErrorCode.LETTER_ELEMENT_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(LetterElementErrorCode.LETTER_ELEMENT_NOT_FOUND_ERROR.getCode(),
                            LetterElementErrorCode.LETTER_ELEMENT_NOT_FOUND_ERROR.getMessage(), letterElementId));
        }
    }
}
