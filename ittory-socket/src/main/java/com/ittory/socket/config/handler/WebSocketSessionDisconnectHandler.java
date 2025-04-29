package com.ittory.socket.config.handler;

import java.util.Map;
import java.util.concurrent.*;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.dto.ExitResponse;
import com.ittory.socket.service.ParticipantSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketSessionDisconnectHandler {
    private final ParticipantSessionService participantSessionService;
    private final SimpMessagingTemplate messagingTemplate;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

    private static final int EXIT_WAIT_TIME = 15;

    public void handleDisconnect(String sessionId) {
        Boolean isExist = participantSessionService.existParticipantBySessionId(sessionId);

        if (isExist) {
            ScheduledFuture<?> future = scheduler.schedule(() -> {
                // 퇴장 처리
                Participant participant = participantSessionService.exitParticipantBySession(sessionId);
                futureMap.remove(sessionId);

                // 메세지 전달
                String destination = "/topic/letter/" + participant.getLetter().getId();
                log.info("Bye {}", sessionId);
                messagingTemplate.convertAndSend(destination, ExitResponse.from(participant, true));
            }, EXIT_WAIT_TIME, TimeUnit.SECONDS);
            futureMap.put(sessionId, future);
        }
    }

    /**
     *
     * 1. 퇴장 예약 취소
     * 2. SessionId 변경
     */
    public void handleReconnect(String sessionId, Long memberId) {
        // 퇴장 예약 취소
        String oldSessionId = participantSessionService.findSessionIdByMemberId(memberId);
        ScheduledFuture<?> future = null;
        if (oldSessionId != null) {
            future = futureMap.remove(oldSessionId);
        }
        if (future != null) {
            future.cancel(false);
            log.info("Cancelled scheduled exit for sessionId={}", sessionId);
        }

        // SessionId 변경
        participantSessionService.changeSessionIdByMemberId(sessionId, memberId);
    }

}
