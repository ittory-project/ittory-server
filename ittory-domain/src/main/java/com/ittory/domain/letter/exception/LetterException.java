package com.ittory.domain.letter.exception;

import static com.ittory.domain.letter.exception.LetterErrorCode.COVER_TYPE_NOT_FOUND_ERROR;
import static com.ittory.domain.letter.exception.LetterErrorCode.FONT_NOT_FOUND_ERROR;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class LetterException extends GlobalException {

    public LetterException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class FontNotFoundException extends LetterException {
        public FontNotFoundException(Long fontId) {
            super(FONT_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(FONT_NOT_FOUND_ERROR.getCode(), FONT_NOT_FOUND_ERROR.getMessage(), fontId));
        }
    }

    public static class CoverTypeNotFoundException extends LetterException {
        public CoverTypeNotFoundException(Long coverTypeId) {
            super(COVER_TYPE_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(COVER_TYPE_NOT_FOUND_ERROR.getCode(),
                            COVER_TYPE_NOT_FOUND_ERROR.getMessage(), coverTypeId));
        }
    }
}