package com.ittory.socket.utils;

import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.dto.ExitResponse;
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
public class WriteTimeManager {

    private final LetterActionService letterActionService;
    private final ParticipantService participantService;
    private final LetterDomainService letterDomainService;
    private final ElementDomainService elementDomainService;

    private final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);
    private final Map<Long, ScheduledFuture<?>> TIMEOUT_TASKS = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    private static final Integer WRITE_TIME = 30;
    private static final Integer TIMEOUT_EJECTION_COUNT = 2;

    public void registerWriteTimer(Long letterId, LocalDateTime startTime, Long participantId, Integer timeWeight) {
        // 1. TIME OUT 시간 결정
        Duration delay = Duration.between(LocalDateTime.now(), startTime.plusSeconds(WRITE_TIME + timeWeight));
        long delayMillis = Math.max(delay.toMillis(), 0);

        // 2. Task 생성
        ScheduledFuture<?> timeoutTask = SCHEDULER.schedule(() -> {
            log.warn("Letter {} TIMEOUT, participant: {}", letterId, participantId);
            Participant participant = participantService.findById(participantId);
            boolean isExited = handleParticipantTimeout(letterId, participant);
            proceedToNextParticipant(letterId, participant, isExited);

            sendTimeoutMessage(letterId);
            log.info("Letter {} TIMEOUT", letterId);

        }, delayMillis, TimeUnit.MILLISECONDS);

        // 3. Task 등록
        TIMEOUT_TASKS.put(letterId, timeoutTask);

        log.info("Register WriteTimer Size = {}", TIMEOUT_TASKS.size());
    }

    private void sendTimeoutMessage(Long letterId) {
        String destination = "/topic/letter/" + letterId;
        SimpleResponse message = SimpleResponse.from(ActionType.TIMEOUT);
        messagingTemplate.convertAndSend(destination, message);
    }

    private boolean handleParticipantTimeout(Long letterId, Participant participant) {
        participantService.changeTimeoutCount(participant, participant.getTimeoutCount()+1);
        if (participant.getTimeoutCount() >= TIMEOUT_EJECTION_COUNT) {
            // 퇴장 로직
            Long memberId = participant.getMember().getId();
            letterActionService.exitFromLetter(memberId, letterId, null);
            log.info("Member {} TIMEOUT Exit from Letter {}", memberId, letterId);

            // 퇴장 메세지
            String destination = "/topic/letter/" + letterId;
            ExitResponse message = ExitResponse.from(participant, false);
            messagingTemplate.convertAndSend(destination, message);
            return true;
        }
        return false;
    }

    private void proceedToNextParticipant(Long letterId, Participant participant, boolean isExited) {
        Participant nextParticipant = participantService.findNextParticipant(letterId, participant, isExited);

        if (nextParticipant != null) {
            LocalDateTime nowTime = LocalDateTime.now();
            elementDomainService.changeProcessDataByLetterId(letterId, nowTime, nextParticipant);
            removeWriteTimer(letterId);
            registerWriteTimer(letterId, nowTime, nextParticipant.getId(), 0);
            log.warn("register WriteTimer for Letter {} with Next Participant {}", letterId, nextParticipant.getId());
        } else {
            letterDomainService.updateLetterStatus(letterId, LetterStatus.COMPLETED);
            log.info("Finish Letter {} with No Participant", letterId);
        }
    }

    public void removeWriteTimer(Long letterId) {
        ScheduledFuture<?> future = TIMEOUT_TASKS.remove(letterId);
        if (future != null) {
            future.cancel(true);
            log.info("Timer Cancel because Letter {}'s elements were written in time.", letterId);
        }

        log.info("Remove WriteTimer Size = {}, future: {}", TIMEOUT_TASKS.size(), future==null);
    }
}
