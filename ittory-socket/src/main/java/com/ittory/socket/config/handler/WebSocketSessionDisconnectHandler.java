package com.ittory.socket.config.handler;

import java.util.Map;
import java.util.concurrent.*;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.dto.ExitResponse;
import com.ittory.socket.service.LetterActionService;
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
    private final LetterActionService letterActionService;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

    private static final int EXIT_WAIT_TIME = 15;

    public void handleDisconnect(String sessionId) {
        Boolean isExist = participantSessionService.existParticipantBySessionId(sessionId);

        if (isExist) {
            ScheduledFuture<?> future = scheduler.schedule(() -> {
                try {
                    // 퇴장 처리
                    Participant participant = participantSessionService.findParticipantBySessionId(sessionId);
                    letterActionService.exitFromLetter(participant.getMember().getId(), participant.getLetter().getId(), sessionId);
                    futureMap.remove(sessionId);

                    // 메세지 전달
                    log.info("Bye {}", sessionId);
                    String destination = "/topic/letter/" + participant.getLetter().getId();
                    messagingTemplate.convertAndSend(destination, ExitResponse.from(participant, true));
                } catch (Exception e) {
                    log.error("An error occurred while processing the disconnect for sessionId={}: {}", sessionId, e.getMessage(), e);
                }
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
