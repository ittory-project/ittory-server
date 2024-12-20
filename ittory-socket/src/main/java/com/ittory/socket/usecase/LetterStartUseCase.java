package com.ittory.socket.usecase;

import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.StartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterStartUseCase {

    private final ParticipantDomainService participantDomainService;
    private final LetterDomainService letterDomainService;

    public StartResponse execute(Long letterId) {
        participantDomainService.updateAllStatusToStart(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.PROGRESS);
        return StartResponse.from(letterId);
    }

}
