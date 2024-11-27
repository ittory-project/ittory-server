package com.ittory.socket.usecase;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.EnterRequest;
import com.ittory.socket.dto.EnterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final ParticipantDomainService participantDomainService;


    @Transactional
    public EnterResponse execute(Long memberId, Long letterId, EnterRequest request) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        participant.changeNickname(request.getNickname());
        return EnterResponse.from(participant);
    }

}
