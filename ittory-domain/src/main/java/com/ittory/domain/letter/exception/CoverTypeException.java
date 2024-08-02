package com.ittory.domain.letter.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

public class CoverTypeException extends GlobalException {

    public CoverTypeException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class CoverTypeNotFoundException extends CoverTypeException {
        public CoverTypeNotFoundException(Long coverTypeId) {
            super(CoverTypeErrorCode.COVER_TYPE_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(CoverTypeErrorCode.COVER_TYPE_NOT_FOUND_ERROR.getCode(),
                            CoverTypeErrorCode.COVER_TYPE_NOT_FOUND_ERROR.getMessage(), coverTypeId));
        }
    }
}
