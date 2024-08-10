package com.ittory.socket.usecase;

import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.service.ParticipantDomainService;
import com.ittory.socket.config.handler.WebSocketSessionHandler;
import com.ittory.socket.dto.ExitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterExitUseCase {

    private final WebSocketSessionHandler webSocketSessionHandler;
    private final ParticipantDomainService participantDomainService;

    public ExitResponse execute(Long memberId, Long letterId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        return ExitResponse.from(participant);
    }

}
