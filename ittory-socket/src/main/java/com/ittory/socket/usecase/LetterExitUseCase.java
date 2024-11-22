package com.ittory.socket.usecase;

import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.config.handler.WebSocketSessionHandler;
import com.ittory.socket.dto.ExitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterExitUseCase {

    private final WebSocketSessionHandler webSocketSessionHandler;
    private final ParticipantDomainService participantDomainService;
    private final ElementDomainService elementDomainService;

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
        Integer elementCount = elementDomainService.countByParticipant(participant);
        if (elementCount > 0) {
            hasNoElement = false;
        }
        return hasNoElement;
    }

    private void changeParticipantOrder(Long letterId, Participant participant) {
        participantDomainService.reorderParticipantsAfterLeave(letterId, participant);
    }

}
