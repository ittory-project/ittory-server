package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterStartInfoResponse;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterStartInfoReadUseCase {

    private final LetterDomainService letterDomainService;
    private final ParticipantDomainService participantDomainService;

    @Transactional(readOnly = true)
    public LetterStartInfoResponse execute(Long letterId) {
        Letter letter = letterDomainService.findLetter(letterId);
        List<Element> elements = letterDomainService.findElementsByLetterId(letterId);
        Integer participantCount = participantDomainService.countProgressByLetterId(letterId);
        return LetterStartInfoResponse.of(letter.getTitle(), participantCount, letter.getRepeatCount(), elements.size());
    }

}
