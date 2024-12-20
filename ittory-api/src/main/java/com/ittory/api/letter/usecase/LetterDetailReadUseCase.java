package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterDetailResponse;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterDetailReadUseCase {

    private final LetterDomainService letterDomainService;

    // 편지 상세 정보 조회
    public LetterDetailResponse execute(Long letterId) {
        Letter letter = letterDomainService.findLetterById(letterId);
        List<Element> elements = letterDomainService.findElementsByLetterId(letterId);
        Set<String> nicknames = elements.stream()
                .filter(element -> element.getParticipant() != null)
                .map(element -> element.getParticipant().getNickname())
                .collect(Collectors.toSet());

        return LetterDetailResponse.of(letter, nicknames.stream().toList(), elements);
    }
}
