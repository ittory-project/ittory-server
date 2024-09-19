package com.ittory.socket.usecase;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.dto.ElementEditData;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.ElementResponse;
import com.ittory.socket.mapper.ElementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterWriteUseCase {

    private final ElementDomainService elementDomainService;
    private final ParticipantDomainService participantDomainService;

    private final ElementMapper elementMapper;

    public ElementResponse execute(Long memberId, Long letterId, ElementRequest request) {
        ElementEditData editData = elementMapper.toElementEditData(request);
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        Element element = elementDomainService.changeContent(letterId, participant, editData);
        return ElementResponse.of(participant, element);
    }

}
