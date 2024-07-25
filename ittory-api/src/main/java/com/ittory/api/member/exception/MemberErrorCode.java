package com.ittory.api.member.exception;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_FOUND_ERROR(BAD_REQUEST, "1000", "존재하지 않는 사용자입니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
