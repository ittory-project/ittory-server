package com.ittory.domain.participant.exception;

import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.exception.GlobalException;

import java.util.List;

import static com.ittory.domain.member.exception.MemberErrorCode.MEMBER_NOT_FOUND_ERROR;
import static com.ittory.domain.member.exception.MemberErrorCode.MEMBER_NOT_PARTICIPATION_ERROR;
import static com.ittory.domain.participant.exception.ParticipantErrorCode.DUPLICATE_PARTICIPANT_ERROR;

public class ParticipantException extends GlobalException {

    public ParticipantException(ErrorStatus status, ErrorInfo<?> errorInfo) {
        super(status, errorInfo);
    }

    public static class ParticipantNotFoundException extends ParticipantException {
        public ParticipantNotFoundException(Long letterId, Long memberId) {

            super(MEMBER_NOT_FOUND_ERROR.getStatus(),
                    new ErrorInfo<>(MEMBER_NOT_PARTICIPATION_ERROR.getCode(), MEMBER_NOT_PARTICIPATION_ERROR.getMessage(),
                            List.of(letterId, memberId)));
        }
    }

    public static class DuplicateParticipantException extends ParticipantException {
        public DuplicateParticipantException(Long letterId, Long memberId) {

            super(DUPLICATE_PARTICIPANT_ERROR.getStatus(),
                    new ErrorInfo<>(DUPLICATE_PARTICIPANT_ERROR.getCode(), DUPLICATE_PARTICIPANT_ERROR.getMessage(),
                            List.of(letterId, memberId)));
        }
    }

}
