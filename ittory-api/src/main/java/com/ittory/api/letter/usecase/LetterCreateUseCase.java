package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterCreateRequest;
import com.ittory.api.letter.dto.LetterCreateResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterCreateUseCase {

    private final LetterDomainService letterDomainService;

    public LetterCreateResponse execute(LetterCreateRequest request) {
        Letter letter = letterDomainService.saveLetter(
                request.getCoverTypeId(),
                request.getFontId(),
//                request.getReceiverId(),
                request.getReceiverName(),
                request.getDeliveryDate(),
                request.getTitle(),
                request.getCoverPhotoUrl()
        );
        return LetterCreateResponse.from(letter);
    }
}
