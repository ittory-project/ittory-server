package com.ittory.domain.member.service;

import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.member.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantDomainService {

    private final ParticipantRepository participantRepository;

    public Participant findParticipant(Long letterId, Long memberId) {
        return participantRepository.findByLetterIdAndMemberId(letterId, memberId)
                .orElseThrow(() -> new ParticipantNotFoundException(letterId, memberId));
    }
}
