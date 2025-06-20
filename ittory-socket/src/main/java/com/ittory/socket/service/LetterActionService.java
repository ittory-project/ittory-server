package com.ittory.socket.service;

import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.enums.ParticipantStatus;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.*;
import com.ittory.socket.utils.ConnectSessionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.ittory.domain.participant.enums.ParticipantStatus.*;

@Service
@RequiredArgsConstructor
public class LetterActionService {

    private final ParticipantDomainService participantDomainService;
    private final ElementDomainService elementDomainService;

    private final ConnectSessionStorage connectSessionStorage;

    @Transactional
    public EnterResponse enterToLetter(Long memberId, Long letterId, String sessionId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        if (!Objects.equals(participant.getParticipantStatus(), PROGRESS)) {
            participant.changeParticipantStatus(ENTER);
        }

        List<ParticipantProfile> participants = participantDomainService.findAllCurrentParticipantsOrderedBySequence(letterId, null)
                .stream()
                .map(ParticipantProfile::from)
                .toList();

        connectSessionStorage.saveSessionId(sessionId, participant);

        return EnterResponse.from(participant, participants);
    }

    @Transactional
    public ExitResponse exitFromLetter(Long memberId, Long letterId, String sessionId) {
        Participant manager = participantDomainService.findManagerByLetterId(letterId);
        Participant nowParticipant = participantDomainService.findParticipant(letterId, memberId);
        changeParticipantOrder(letterId, nowParticipant);
        changeParticipantStatus(nowParticipant);

        if (sessionId != null) {
            connectSessionStorage.removeBySessionId(sessionId);
        }

        return ExitResponse.from(nowParticipant, Objects.equals(manager.getId(), nowParticipant.getId()));
    }

    private void changeParticipantStatus(Participant participant) {
        if (!checkNoElement(participant)) {
            participant.changeParticipantStatus(GUEST);
        } else {
            participant.changeParticipantStatus(EXITED);
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

    public DeleteResponse deleteLetter(Long memberId, Long letterId) {
        Participant manager = participantDomainService.findManagerByLetterId(letterId);
        if (!manager.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("Manager did not match");
        }
        participantDomainService.updateAllStatusToDelete(letterId);
        return DeleteResponse.from(letterId);
    }


}
