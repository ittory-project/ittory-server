package com.ittory.domain.participant.repository.impl;

import com.ittory.domain.participant.domain.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepositoryCustom {
    Optional<Participant> findByLetterIdAndMemberId(Long letterId, Long memberId);

    List<Participant> findCurrentParticipantsByLetterIdOrdered(Long letterId, Boolean isAscending);

    List<Participant> findAllOrderNext(Long letterId, Integer sequence);

}
