package com.ittory.socket.usecase;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.enums.ParticipantStatus;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.EnterResponse;
import com.ittory.socket.dto.ParticipantProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final ParticipantDomainService participantDomainService;


    @Transactional
    public EnterResponse execute(Long memberId, Long letterId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        participant.changeParticipantStatus(ParticipantStatus.ENTER);
        List<ParticipantProfile> participants = participantDomainService.findAllCurrentParticipantsOrderedBySequence(letterId, null)
                .stream()
                .map(ParticipantProfile::from)
                .toList();
        return EnterResponse.from(participant, participants);
    }

}
