package com.ittory.socket.usecase;

import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.service.ParticipantDomainService;
import com.ittory.socket.config.handler.WebSocketSessionHandler;
import com.ittory.socket.dto.EnterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterEnterUseCase {

    private final WebSocketSessionHandler webSocketSessionHandler;
    private final ParticipantDomainService participantDomainService;

    public EnterResponse execute(Long memberId, Long letterId) {
//        webSocketSessionHandler.putSession(memberId, letterId);
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        return EnterResponse.from(participant);
    }

}
