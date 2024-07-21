package com.ittory.api.common.exception;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;
import static com.ittory.common.exception.ErrorStatus.INTERNAL_SERVER;

import com.ittory.common.exception.CommonErrorCode;
import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Error: {}, Class : {}, Message : {}";
    private static final String LOG_CODE_FORMAT = "Error: {}, Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse<?>> applicationException(GlobalException exception) {

        log.error(
                LOG_CODE_FORMAT,
                "GlobalException",
                exception.getClass().getSimpleName(),
                exception.getErrorInfo(),
                exception.getMessage()
        );
        HttpStatus status = HttpStatus.valueOf(exception.getStatus().getValue());

        return ResponseEntity.status(status)
                .body(ErrorResponse.of(exception.getStatus(), exception.getErrorInfo()));

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception) {
        log.error(
                LOG_FORMAT,
                "MethodArgumentTypeMismatchException",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );

        CommonErrorCode errorCode = CommonErrorCode.ENUM_ERROR;
        ErrorInfo<?> errorInfo = new ErrorInfo<>(errorCode.getCode(), errorCode.getMessage(),
                exception.getName());

        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(BAD_REQUEST, errorInfo));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse<?>> handleRuntimeException(RuntimeException exception) {

        log.error(
                LOG_FORMAT,
                "RuntimeException",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );

        CommonErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        ErrorInfo<?> errorInfo = new ErrorInfo<>(errorCode.getCode(), errorCode.getMessage(), null);

        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(INTERNAL_SERVER, errorInfo));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse<?>> handleDataAccessException(DataAccessException exception) {

        log.error(
                LOG_FORMAT,
                "DataAccessException",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );

        CommonErrorCode errorCode = CommonErrorCode.DB_ACCESS_ERROR;
        ErrorInfo<?> errorInfo = new ErrorInfo<>(errorCode.getCode(), errorCode.getMessage(), null);

        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(INTERNAL_SERVER, errorInfo));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleException(Exception exception) {

        log.error(
                LOG_FORMAT,
                "Exception",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );

        CommonErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        ErrorInfo<?> errorInfo = new ErrorInfo<>(errorCode.getCode(), errorCode.getMessage(), null);

        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(INTERNAL_SERVER, errorInfo));
    }

}
