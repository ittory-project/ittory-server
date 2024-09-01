package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterRepeatCountRequest;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterRepeatCountUseCase {

    private final LetterDomainService letterDomainService;

    public void execute(LetterRepeatCountRequest request) {
        letterDomainService.createLetterElements(request.getLetterId(), request.getRepeatCount());
    }
}
