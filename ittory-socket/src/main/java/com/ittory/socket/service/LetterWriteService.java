package com.ittory.socket.service;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.dto.ElementEditData;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.SubmitResponse;
import com.ittory.socket.utils.ElementWriteTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LetterWriteService {

    private final ParticipantService participantService;

    private final ParticipantDomainService participantDomainService;
    private final ElementDomainService elementDomainService;
    private final ElementWriteTimer elementWriteTimer;

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
        Participant nextParticipant = participantService.findNextParticipant(letterId, currentElement.getParticipant());
        LocalDateTime now = LocalDateTime.now();
        Element nextElement = prepareNextElement(letterId, currentElement, nextParticipant, now);

        if (nextElement != null) {
            elementWriteTimer.registerWriteTimer(letterId, now, nextParticipant);
        }

        return SubmitResponse.of(currentElement, nextElement);
    }

    private Element updateCurrentElement(Long memberId, Long letterId, ElementRequest request) {
        ElementEditData editData = ElementEditData.of(request.getElementId(), request.getContent());
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        participant.changeTimeoutCount(0);
        return elementDomainService.changeContent(participant, editData);
    }

    private Element prepareNextElement(Long letterId, Element currentElement, Participant nextParticipant, LocalDateTime now) {
        Element nextElement = elementDomainService.findNextElementByLetterIdAndSequence(letterId, currentElement.getSequence());
        if (nextElement != null) {
            nextElement.changeStartTime(now);
            nextElement.changeParticipant(nextParticipant);
        }
        return nextElement;
    }
}
