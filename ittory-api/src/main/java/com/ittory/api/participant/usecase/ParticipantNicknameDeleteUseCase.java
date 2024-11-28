package com.ittory.api.participant.usecase;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantNicknameDeleteUseCase {

    private final ParticipantDomainService participantDomainService;

    public void execute(Long memberId, Long letterId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        participant.changeNickname(null);
        participantDomainService.saveParticipant(participant);
    }

}
