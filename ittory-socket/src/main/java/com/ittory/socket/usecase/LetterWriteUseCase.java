package com.ittory.socket.usecase;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.ElementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterWriteUseCase {

    private final ElementDomainService elementDomainService;
    private final ParticipantDomainService participantDomainService;

    public ElementResponse execute(Long memberId, ElementRequest request) {
        Participant participant = participantDomainService.findParticipant(request.getElementId(), memberId);
        Element element = elementDomainService.changeContent(participant, request.getElementId(),
                request.getContent());
        return ElementResponse.of(participant, element);
    }

}
