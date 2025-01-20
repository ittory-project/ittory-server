package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterRepeatCountRequest;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LetterRepeatCountUseCase {

    private final LetterDomainService letterDomainService;
    private final ElementDomainService elementDomainService;

    @Transactional
    public void execute(LetterRepeatCountRequest request) {
        Letter letter = letterDomainService.findLetter(request.getLetterId());
        elementDomainService.deleteAllByLetterId(letter.getId());
        letterDomainService.changeRepeatCount(letter, request.getRepeatCount());
        letterDomainService.createLetterElements(letter, request.getRepeatCount());
    }
}
