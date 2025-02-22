package com.ittory.socket.service;

import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.EndResponse;
import com.ittory.socket.dto.StartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LetterProcessService {

    private final ParticipantDomainService participantDomainService;
    private final LetterDomainService letterDomainService;

    public StartResponse startLetter(Long letterId) {
        participantDomainService.updateAllStatusToStart(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.PROGRESS);
        return StartResponse.from(letterId);
    }

    public EndResponse endLetter(Long letterId) {
        participantDomainService.updateAllStatusToEnd(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.COMPLETED);
        return EndResponse.from(letterId);
    }
}
