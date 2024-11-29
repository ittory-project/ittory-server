package com.ittory.socket.usecase;

import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.StartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterStartUseCase {

    private final ParticipantDomainService participantDomainService;

    public StartResponse execute(Long letterId) {
        participantDomainService.updateAllStatusToStart(letterId);
        return StartResponse.from(letterId);
    }

}
