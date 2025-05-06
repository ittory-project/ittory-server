package com.ittory.socket.utils;

import com.ittory.socket.dto.SimpleResponse;
import com.ittory.socket.enums.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElementWriteTimer {

    private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);
    private final Map<Long, ScheduledFuture<?>> TIMEOUT_TASKS = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    private static final Integer WRITE_TIME = 100;

    public void registerWriteTimer(Long letterId, LocalDateTime startTime) {
        // 1. TIME OUT 시간 결정
        Duration delay = Duration.between(LocalDateTime.now(), startTime.plusSeconds(WRITE_TIME));
        long delayMillis = Math.max(delay.toMillis(), 0);

        // 2. 타이머 생성
        ScheduledFuture<?> timeoutTask = SCHEDULER.schedule(() -> {
            messagingTemplate.convertAndSend(
                    "/topic/letter/" + letterId,
                    SimpleResponse.from(ActionType.TIMEOUT)
            );
            TIMEOUT_TASKS.remove(letterId);
            log.info("Letter {} TIMEOUT", letterId);

        }, delayMillis, TimeUnit.MILLISECONDS);

        // 3. 타이머 등록
        TIMEOUT_TASKS.put(letterId, timeoutTask);

        log.warn("WriteTimer Size = {}", TIMEOUT_TASKS.size());
    }

    public void removeWriteTimer(Long letterId) {
        ScheduledFuture<?> future = TIMEOUT_TASKS.remove(letterId);
        if (future != null) {
            future.cancel(false);
            log.info("Letter {}'s elements were written in time.", letterId);
        }

        log.warn("WriteTimer Size = {}", TIMEOUT_TASKS.size());
    }
}
