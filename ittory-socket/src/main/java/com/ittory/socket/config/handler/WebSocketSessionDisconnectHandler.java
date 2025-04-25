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
        ScheduledFuture<?> future = scheduler.schedule(() -> {
            try {
                Participant participant = participantSessionService.exitUserBySession(sessionId);
                futureMap.remove(sessionId);
                if (participant != null) {
                    String destination = "/topic/letter/" + participant.getLetter().getId();
                    log.info("Bye {}", sessionId);
                    messagingTemplate.convertAndSend(destination, ExitResponse.from(participant, true));
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }, EXIT_WAIT_TIME, TimeUnit.SECONDS);
        futureMap.put(sessionId, future);
    }

}
