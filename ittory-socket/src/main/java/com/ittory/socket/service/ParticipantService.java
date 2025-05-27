package com.ittory.socket.service;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantDomainService participantDomainService;

    @Transactional
    public Participant changeTimeoutCount(Participant participant, int timeoutCount) {
        participant.changeTimeoutCount(timeoutCount);
        participantDomainService.saveParticipant(participant);
        return participant;
    }

    @Transactional(readOnly = true)
    public Participant findNextParticipant(Long letterId, Participant currentParticipant, boolean isExited) {
        Integer totalParticipants = participantDomainService.findAllNowParticipants(letterId).size();
        if (totalParticipants == 0) {
            return null;
        }
        int nextSequence = (currentParticipant.getSequence() % totalParticipants) + 1;

        if (isExited && nextSequence > 1) {
            nextSequence--;
        }
        return participantDomainService.findParticipantBySequence(letterId, nextSequence);
    }

    @Transactional(readOnly = true)
    public Participant findById(Long participantId) {
        return participantDomainService.findById(participantId);
    }

}
