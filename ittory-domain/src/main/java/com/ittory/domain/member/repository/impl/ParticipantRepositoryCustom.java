package com.ittory.domain.member.repository.impl;

import com.ittory.domain.member.domain.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepositoryCustom {
    Optional<Participant> findByLetterIdAndMemberId(Long letterId, Long memberId);

    List<Participant> findAllCurrentByIdWithMember(Long letterId);

    List<Participant> findAllOrderNext(Long letterId, Integer sort);
}
