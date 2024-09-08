package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.LetterEnterStatusResponse;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterEnterStatusCheckUseCase {

    private final ParticipantDomainService participantDomainService;

    public LetterEnterStatusResponse execute(Long letterId) {
        Boolean enterStatus = participantDomainService.getEnterStatus(letterId);
        return LetterEnterStatusResponse.create(enterStatus);
    }

}
