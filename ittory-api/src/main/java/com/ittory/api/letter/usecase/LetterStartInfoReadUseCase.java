package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterStartInfoResponse;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterStartInfoReadUseCase {

    private final LetterDomainService letterDomainService;
    private final ParticipantDomainService participantDomainService;

    public LetterStartInfoResponse execute(Long letterId) {
        Letter letter = letterDomainService.findLetter(letterId);
        List<Element> elements = letterDomainService.findElementsByLetterId(letterId);
        List<Participant> participants = participantDomainService.findAllParticipants(letterId);
        return LetterStartInfoResponse.of(participants.size(), letter.getRepeatCount(), elements.size());
    }

}
