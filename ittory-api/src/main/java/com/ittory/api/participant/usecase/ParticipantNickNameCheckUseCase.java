package com.ittory.api.participant.usecase;

import com.ittory.api.participant.dto.NicknameDuplicationResultResponse;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantNickNameCheckUseCase {

    private final ParticipantDomainService participantDomainService;

    public NicknameDuplicationResultResponse execute(Long letterId, String nickname) {
        Boolean isDuplicate = participantDomainService.checkNicknameDuplication(letterId, nickname);
        return NicknameDuplicationResultResponse.from(isDuplicate);
    }

}
