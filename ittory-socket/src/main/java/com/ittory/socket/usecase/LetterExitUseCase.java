package com.ittory.socket.usecase;

import com.ittory.domain.letter.service.LetterElementDomainService;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.service.ParticipantDomainService;
import com.ittory.socket.config.handler.WebSocketSessionHandler;
import com.ittory.socket.dto.ExitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterExitUseCase {

    private final WebSocketSessionHandler webSocketSessionHandler;
    private final ParticipantDomainService participantDomainService;
    private final LetterElementDomainService letterElementDomainService;

    public ExitResponse execute(Long memberId, Long letterId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        changeParticipantOrder(letterId, participant);
        exitParticipant(participant);
        return ExitResponse.from(participant);
    }

    private void exitParticipant(Participant participant) {
        if (!checkNoElement(participant)) {
            participantDomainService.exitParticipant(participant);
        } else {
            participantDomainService.deleteParticipant(participant);
        }
    }

    private boolean checkNoElement(Participant participant) {
        boolean hasNoElement = true;
        Integer elementCount = letterElementDomainService.countByParticipant(participant);
        if (elementCount > 0) {
            hasNoElement = false;
        }
        return hasNoElement;
    }

    private void changeParticipantOrder(Long letterId, Participant participant) {
        participantDomainService.changeOrder(letterId, participant);
    }

}
