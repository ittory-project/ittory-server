package com.ittory.domain.participant.repository.impl;

import com.ittory.domain.participant.domain.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepositoryCustom {
    Optional<Participant> findByLetterIdAndMemberId(Long letterId, Long memberId);

    List<Participant> findCurrentParticipantsByLetterIdOrdered(Long letterId, Boolean isAscending);

    List<Participant> findAllOrderNext(Long letterId, Integer sequence);

    List<Participant> findAllParticipantsWithMember(Long letterId);

    Participant findEnterByNickname(Long letterId, String nickname);

    Integer countProgressByLetterId(Long letterId);

    Participant findManagerByLetterId(Long letterId);

    void updateAllStatusToStart(Long letterId);

    void updateAllStatusToEnd(Long letterId);

    void updateAllStatusToDelete(Long letterId);

    Integer countEnterParticipantByLetterId(Long letterId);

    Optional<Participant> findEnterParticipantByLetterIdAndMemberId(Long letterId, Long memberId);

    Optional<Participant> findByLetterIdAndSequence(Long letterId, Integer sequence);

    List<Participant> findAllProgressParticipantsWithMember(Long letterId);
}
