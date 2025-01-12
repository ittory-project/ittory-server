package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterInfoResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterInfoReadUseCase {

    private final LetterDomainService letterDomainService;

    // 편지 기본 정보 조회
//    public LetterInfoResponse execute(Long letterId) {
//        Letter letter = letterDomainService.findLetterById(letterId);
//        return LetterInfoResponse.from(letter);
//    }

    public LetterInfoResponse execute(Long letterId) {
        Letter letter = letterDomainService.findLetterById(letterId);
        return LetterInfoResponse.from(letter);
    }
}
