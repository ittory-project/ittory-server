package com.ittory.api.letter.usecase;

import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterDeleteUseCase {

    private final LetterDomainService letterDomainService;

    public void execute(Long letterId) {
        letterDomainService.deleteLetter(letterId);
    }
}
