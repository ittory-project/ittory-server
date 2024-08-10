package com.ittory.domain.member.exception;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParticipantErrorCode {

    PARTICIPANT_NOT_FOUNT_ERROR(BAD_REQUEST, "7000", "존재하지 않는 참여자입니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
