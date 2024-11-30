package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterElementRequest;
import com.ittory.api.letter.dto.LetterElementResponse;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterElementUseCase {

    private final LetterDomainService letterDomainService;
    private final ElementDomainService elementDomainService;

    public LetterElementResponse execute(Long letterElementId, LetterElementRequest request) {
        Element element = elementDomainService.findById(letterElementId);
        letterDomainService.updateLetterElement(letterElementId, request.getContent());
        return new LetterElementResponse(letterElementId, element.getParticipant().getNickname(),
                element.getElementImage().getUrl(), request.getContent(), element.getSequence());
    }
}
