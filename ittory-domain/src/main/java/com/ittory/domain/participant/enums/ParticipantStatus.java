package com.ittory.domain.participant.enums;

public enum ParticipantStatus {
    ENTER, // 편지 작성 대기중
    PROGRESS, // 편지 장성중
    COMPLETED, // 편지 작성 완료
    EXITED, // 1개 이상 작성 후 퇴장
    GHOST // 그냥 퇴장 or 닉네임 등록 이전 퇴장
}
