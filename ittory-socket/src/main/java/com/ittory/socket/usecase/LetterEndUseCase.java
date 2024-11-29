package com.ittory.socket.usecase;

import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.EndResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterEndUseCase {

    private final ParticipantDomainService participantDomainService;

    public EndResponse execute(Long letterId) {
        participantDomainService.updateAllStatusToEnd(letterId);
        return EndResponse.from(letterId);
    }

}
