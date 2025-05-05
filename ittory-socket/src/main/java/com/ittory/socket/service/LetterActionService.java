package com.ittory.socket.service;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.dto.ElementEditData;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.enums.ParticipantStatus;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.*;
import com.ittory.socket.utils.ElementWriteTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.ittory.domain.participant.enums.ParticipantStatus.EXITED;
import static com.ittory.domain.participant.enums.ParticipantStatus.GUEST;

@Service
@RequiredArgsConstructor
public class LetterActionService {

    private final ParticipantDomainService participantDomainService;
    private final ElementDomainService elementDomainService;

    private final ElementWriteTimer elementWriteTimer;

    @Transactional
    public EnterResponse enterToLetter(Long memberId, Long letterId, String sessionId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        participant.changeParticipantStatus(ParticipantStatus.ENTER);
        List<ParticipantProfile> participants = participantDomainService.findAllCurrentParticipantsOrderedBySequence(letterId, null)
                .stream()
                .map(ParticipantProfile::from)
                .toList();

        return EnterResponse.from(participant, participants);
    }

    @Transactional
    public ExitResponse exitFromLetter(Long memberId, Long letterId, String sessionId) {
        Participant manager = participantDomainService.findManagerByLetterId(letterId);
        Participant nowParticipant = participantDomainService.findParticipant(letterId, memberId);
        changeParticipantOrder(letterId, nowParticipant);
        changeParticipantStatus(nowParticipant);
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

    /**
     * = 편지 요소 작성 =
     * 1. 작성 타이머 제거
     * 2. 작성 내용 등록
     * 3. 다음 참여자 계산
     * 4. 다음 요소 설정
     * 5. 작성 타이머 재설정
     */
    @Transactional
    public SubmitResponse writeElement(Long memberId, Long letterId, ElementRequest request) {
        elementWriteTimer.removeWriteTimer(letterId);

        Element currentElement = updateCurrentElement(memberId, letterId, request);
        Participant nextParticipant = calculateNextParticipant(letterId, currentElement.getParticipant());
        LocalDateTime now = LocalDateTime.now();
        Element nextElement = prepareNextElement(letterId, currentElement, nextParticipant, now);

        if (nextElement != null) {
            elementWriteTimer.registerWriteTimer(letterId, now);
        }

        return SubmitResponse.of(currentElement, nextElement);
    }

    private Element updateCurrentElement(Long memberId, Long letterId, ElementRequest request) {
        ElementEditData editData = ElementEditData.of(request.getElementId(), request.getContent());
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        return elementDomainService.changeContent(participant, editData);
    }

    private Participant calculateNextParticipant(Long letterId, Participant currentParticipant) {
        int totalParticipants = participantDomainService.findAllParticipants(letterId).size();
        int nextSequence = (currentParticipant.getSequence() % totalParticipants) + 1;
        return participantDomainService.findParticipantBySequence(letterId, nextSequence);
    }

    private Element prepareNextElement(Long letterId, Element currentElement, Participant nextParticipant, LocalDateTime now) {
        Element nextElement = elementDomainService.findNextElementByLetterIdAndSequence(letterId, currentElement.getSequence());
        if (nextElement != null) {
            nextElement.changeStartTime(now);
            nextElement.changeParticipant(nextParticipant);
        }
        return nextElement;
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
