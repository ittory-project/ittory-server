package com.ittory.socket.service;

import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.EndResponse;
import com.ittory.socket.dto.StartResponse;
import com.ittory.socket.utils.ElementWriteTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class LetterProcessService {

    private final ParticipantDomainService participantDomainService;
    private final LetterDomainService letterDomainService;
    private final ElementDomainService elementDomainService;

    private final ElementWriteTimer elementWriteTimer;

    @Transactional
    public StartResponse startLetter(Long letterId) {
        // 상태 변경
        participantDomainService.updateAllStatusToStart(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.PROGRESS);

        // 시작시간과 작성자 설정
        Participant participant = participantDomainService.findParticipantBySequence(letterId, 1);
        LocalDateTime now = LocalDateTime.now();
        elementDomainService.updateStartTimeAndWriter(letterId, 1, participant, now);

        // 타이머 설정
        elementWriteTimer.registerWriteTimer(letterId, now);

        return StartResponse.from(letterId);
    }

    public EndResponse endLetter(Long letterId) {
        participantDomainService.updateAllStatusToEnd(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.COMPLETED);
        return EndResponse.from(letterId);
    }
}
