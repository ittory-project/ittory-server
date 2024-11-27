package com.ittory.socket.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final ParticipantDomainService participantDomainService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws JsonProcessingException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Object simpSessionAttributes = headerAccessor.getHeader("simpSessionAttributes");

        Long memberId = objectMapper.readValue(simpSessionAttributes.toString().split("}")[0].split("=")[1], Long.class);

        participantDomainService.deleteParticipantByMemberIdWhenDisconnect(memberId);

    }
}
