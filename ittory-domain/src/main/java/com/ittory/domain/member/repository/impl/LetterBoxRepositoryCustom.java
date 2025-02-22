package com.ittory.domain.member.repository.impl;

import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.enums.LetterBoxType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LetterBoxRepositoryCustom {
    Boolean existsInLetterBox(Long letterId);

    //    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<LetterBox> findReceivedByLetterIdWithLock(Long letterId);

    Integer countParticipationLetterByMemberId(Long memberId);

    Integer countReceiveLetterByMemberId(Long memberId);

    long countReceivedLetterByCreatedAt(LocalDate date);

    List<LetterBox> findAllByMemberIdAndLetterBoxTypeWithFetch(Long memberId, LetterBoxType letterBoxType);
}
