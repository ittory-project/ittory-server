package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterStorageStatusResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.service.LetterBoxDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterStorageStatusCheckUseCase {

    private final LetterDomainService letterDomainService;
    private final LetterBoxDomainService letterBoxDomainService;

    public LetterStorageStatusResponse execute(Long letterId) {
        Letter letter = letterDomainService.findLetter(letterId);
        Boolean isStored = letterBoxDomainService.checkStorageStatus(letter.getId());
        return LetterStorageStatusResponse.of(isStored, letter.getReceiverName());
    }

}
