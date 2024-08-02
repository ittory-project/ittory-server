package com.ittory.domain.letter.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

import static com.ittory.domain.letter.exception.FontErrorCode.FONT_NOT_FOUND_ERROR;

public class FontException extends GlobalException {

    public FontException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class FontNotFoundException extends FontException {
        public FontNotFoundException(Long fontId) {
            super(FONT_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(FONT_NOT_FOUND_ERROR.getCode(), FONT_NOT_FOUND_ERROR.getMessage(), fontId));
        }
    }
}