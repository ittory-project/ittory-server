package com.ittory.socket.usecase;

import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.letter.service.LetterElementDomainService;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.service.ParticipantDomainService;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.ElementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterWriteUseCase {

    private final LetterElementDomainService letterElementDomainService;
    private final ParticipantDomainService participantDomainService;

    public ElementResponse execute(Long memberId, ElementRequest request) {
        Participant participant = participantDomainService.findParticipant(request.getElementId(), memberId);
        LetterElement element = letterElementDomainService.changeContent(participant, request.getElementId(),
                request.getContent());
        return ElementResponse.of(participant, element);
    }

}
