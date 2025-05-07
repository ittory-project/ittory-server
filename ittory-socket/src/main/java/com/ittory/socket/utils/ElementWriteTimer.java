package com.ittory.socket.utils;

import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.dto.SimpleResponse;
import com.ittory.socket.enums.ActionType;
import com.ittory.socket.service.LetterActionService;
import com.ittory.socket.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ElementWriteTimer {

    private final LetterActionService letterActionService;
    private final ParticipantService participantService;
    private final LetterDomainService letterDomainService;

    private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);
    private final Map<Long, ScheduledFuture<?>> TIMEOUT_TASKS = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    private static final Integer WRITE_TIME = 5;

    public void registerWriteTimer(Long letterId, LocalDateTime startTime, Participant participant) {
        // 1. TIME OUT 시간 결정
        Duration delay = Duration.between(LocalDateTime.now(), startTime.plusSeconds(WRITE_TIME));
        long delayMillis = Math.max(delay.toMillis(), 0);

        // 2. 타이머 생성
        ScheduledFuture<?> timeoutTask = SCHEDULER.schedule(() -> {
            // TimeOut 보내기
            String destination = "/topic/letter/" + letterId;
            SimpleResponse message = SimpleResponse.from(ActionType.TIMEOUT);
            messagingTemplate.convertAndSend(destination, message);
            TIMEOUT_TASKS.remove(letterId);
            log.info("Letter {} TIMEOUT", letterId);

            // TimeOut Count 계산
            participantService.changeTimeoutCount(participant, participant.getTimeoutCount()+1);
            if (participant.getTimeoutCount() >= 2) {
                Long memberId = participant.getMember().getId();
                letterActionService.exitFromLetter(memberId, letterId);
                log.info("Member {} TIMEOUT Exit from Letter {}", memberId, letterId);
            }

            // NextTimer 설정
            Participant nextParticipant = participantService.findNextParticipant(letterId, participant);
            if (nextParticipant != null) {
                registerWriteTimer(letterId, LocalDateTime.now(), nextParticipant);
            } else {
                letterDomainService.updateLetterStatus(letterId, LetterStatus.COMPLETED);
            }

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
