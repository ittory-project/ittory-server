package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterElementRequest;
import com.ittory.api.letter.dto.LetterElementResponse;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterElementUseCase {

    private final LetterDomainService letterDomainService;

    public LetterElementResponse execute(Long letterElementId, LetterElementRequest request) {
        letterDomainService.updateLetterElement(letterElementId, request.getContent());
        return new LetterElementResponse(letterElementId, request.getContent());
    }
}
