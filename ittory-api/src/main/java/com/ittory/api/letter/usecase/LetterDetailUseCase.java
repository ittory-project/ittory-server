package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterDetailResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterDetailUseCase {

    private final LetterDomainService letterDomainService;

    // 편지 상세 정보 조회
    public LetterDetailResponse execute(Long letterId) {
        Letter letter = letterDomainService.findLetterById(letterId);
        List<LetterElement> elements = letterDomainService.findElementsByLetterId(letterId);
        return LetterDetailResponse.from(letter, elements);
    }
}
