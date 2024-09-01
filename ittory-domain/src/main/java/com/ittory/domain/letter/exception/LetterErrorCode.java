package com.ittory.domain.letter.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LetterErrorCode {

    LETTER_NOT_FOUND_ERROR(ErrorStatus.NOT_FOUND, "1003", "해당 편지를 찾을 수 없습니다."),
    FONT_NOT_FOUND_ERROR(ErrorStatus.NOT_FOUND, "1001", "해당 폰트를 찾을 수 없습니다."),
    COVER_TYPE_NOT_FOUND_ERROR(ErrorStatus.NOT_FOUND, "1002", "해당 표지를 찾을 수 없습니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
