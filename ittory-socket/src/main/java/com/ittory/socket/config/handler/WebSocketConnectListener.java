package com.ittory.socket.config.handler;

import com.ittory.common.jwt.AccessTokenInfo;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.service.ParticipantSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketConnectListener {

    private final JwtProvider jwtProvider;
    private final WebSocketSessionDisconnectHandler webSocketSessionDisconnectHandler;
    private final ParticipantSessionService participantSessionService;

    @EventListener
    public void onConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        Long memberId = getMemberId(accessor);

        Participant participant = null;
        if (memberId != null) {
            participant = participantSessionService.findParticipantByMemberId(memberId);
        }

        if (participant != null) {
            log.info("Reconnected: SessionId = {}, MemberId = {}", sessionId, memberId);
            webSocketSessionDisconnectHandler.handleReconnect(sessionId, memberId);
        } else {
            log.info("Connected: SessionId = {}, MemberId = {}", sessionId, memberId);
        }
    }

    private Long getMemberId(StompHeaderAccessor accessor) {
        List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
        if (authorizationHeaders == null || authorizationHeaders.isEmpty()) {
            log.warn("No Authorization header found.");
            return null;
        }

        String token = authorizationHeaders.get(0);
        AccessTokenInfo accessTokenInfo = jwtProvider.resolveToken(token);
        return Long.parseLong(accessTokenInfo.getMemberId());
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        Long memberId = null;
        if (Objects.requireNonNull(accessor.getSessionAttributes()).containsKey("member_id")) {
            memberId = Long.parseLong(accessor.getSessionAttributes().get("member_id").toString());
        }

        webSocketSessionDisconnectHandler.handleDisconnect(sessionId);
        log.info("Disconnect: SessionId = {}, MemberId = {}", sessionId, memberId);
    }
}


