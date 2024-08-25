package com.ittory.domain.letter.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LetterElementErrorCode {

    LETTER_ELEMENT_NOT_FOUND_ERROR(ErrorStatus.NOT_FOUND, "1004", "해당 편지 요소를 찾을 수 없습니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
