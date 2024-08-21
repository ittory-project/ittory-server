package com.ittory.domain.participant.repository.impl;

import com.ittory.domain.participant.domain.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepositoryCustom {
    Optional<Participant> findByLetterIdAndMemberId(Long letterId, Long memberId);

    List<Participant> findAllCurrentByIdWithMember(Long letterId);

    List<Participant> findAllOrderNext(Long letterId, Integer sequence);

    List<Participant> findAllCurrentByIdWithMemberInOrder(Long letterId, boolean isAsc);
}
