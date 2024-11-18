package com.ittory.domain.member.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_FOUND_ERROR(ErrorStatus.BAD_REQUEST, "1000", "존재하지 않는 사용자입니다."),
    LETTER_BOX_ALREADY_STORED(ErrorStatus.BAD_REQUEST, "1001", "이미 저장된 편지입니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
