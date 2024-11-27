package com.ittory.api.participant.usecase;

import com.ittory.api.participant.dto.ParticipantNicknameRequest;
import com.ittory.api.participant.dto.ParticipantNicknameResponse;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantNicknameUpdateUseCase {

    private final ParticipantDomainService participantDomainService;

    public synchronized ParticipantNicknameResponse execute(Long memberId, ParticipantNicknameRequest request) {
        Boolean isDuplicate = participantDomainService.checkNicknameDuplication(request.getLetterId(), request.getNickname());

        String nickname = null;
        if (!isDuplicate) {
            Participant participant = participantDomainService.findParticipant(request.getLetterId(), memberId);
            participant.changeNickname(request.getNickname());
            participantDomainService.saveParticipant(participant);
            nickname = request.getNickname();
        }

        return ParticipantNicknameResponse.of(!isDuplicate, nickname);
    }
}
