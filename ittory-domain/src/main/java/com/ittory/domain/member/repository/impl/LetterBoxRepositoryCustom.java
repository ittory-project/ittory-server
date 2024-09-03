package com.ittory.domain.member.repository.impl;

import com.ittory.domain.member.domain.LetterBox;
import java.util.Optional;

public interface LetterBoxRepositoryCustom {
    Boolean existsInLetterBox(Long letterId);

    //    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<LetterBox> findReceivedByLetterIdWithLock(Long letterId);

    Integer countParticipationLetterByMemberId(Long memberId);

    Integer countReceiveLetterByMemberId(Long memberId);
}
