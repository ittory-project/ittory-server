package com.ittory.domain.participant.exception;

import com.ittory.common.exception.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ittory.common.exception.ErrorStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ParticipantErrorCode {

    PARTICIPANT_NOT_FOUNT_ERROR(BAD_REQUEST, "7000", "존재하지 않는 참여자입니다."),
    DUPLICATE_PARTICIPANT_ERROR(BAD_REQUEST, "7001", "이미 참여중인 사용자입니다."),
    DUPLICATE_NICKNAME_ERROR(BAD_REQUEST, "7002", "중복된 닉네임입니다.");

    private final ErrorStatus status;
    private final String code;
    private final String message;
}
