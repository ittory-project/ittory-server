package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterCreateRequest;
import com.ittory.api.letter.dto.LetterCreateResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LetterCreateUseCase {

    private final LetterDomainService letterDomainService;

    @Transactional
    public LetterCreateResponse execute(LetterCreateRequest request) {
        Letter letter = letterDomainService.saveLetter(
                request.getCoverTypeId(),
                request.getFontId(),
                request.getReceiverName(),
                request.getDeliveryDate(),
                request.getTitle(),
                request.getCoverPhotoUrl()
        );
        return LetterCreateResponse.from(letter);
    }
}
