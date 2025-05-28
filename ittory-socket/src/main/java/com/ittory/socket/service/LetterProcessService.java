package com.ittory.socket.service;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.StartResponse;
import com.ittory.socket.utils.WriteTimeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LetterProcessService {

    private final ParticipantDomainService participantDomainService;
    private final LetterDomainService letterDomainService;
    private final ElementDomainService elementDomainService;
    private final LetterBoxDomainService letterBoxDomainService;
    private final ParticipantSessionService participantSessionService;

    private final WriteTimeManager writeTimeManager;

    private static final Integer START_WEIGHT_TIME= 15;

    @Transactional
    public StartResponse startLetter(Long letterId) {
        // 상태 변경
        participantDomainService.updateAllStatusToStart(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.PROGRESS);

        // 시작시간과 작성자 설정
        Participant participant = participantDomainService.findParticipantBySequence(letterId, 1);
        LocalDateTime now = LocalDateTime.now().plusSeconds(START_WEIGHT_TIME);
        elementDomainService.updateStartTimeAndWriter(letterId, 1, participant, now);

        // ConnectSession 삭제
        List<Participant> allParticipants = participantDomainService.findAllParticipants(letterId);
        participantSessionService.removeAllSessions(allParticipants);

        // 타이머 설정
        writeTimeManager.registerWriteTimer(letterId, now, participant.getId());

        return StartResponse.from(letterId);
    }

    public void finishLetter(Long letterId) {
        // 타이머 제거
        writeTimeManager.removeWriteTimer(letterId);

        // 관련된 데이터의 상제 변경
        participantDomainService.updateAllStatusToEnd(letterId);
        letterDomainService.updateLetterStatus(letterId, LetterStatus.COMPLETED);

        // 참여자들의 편지함에 편지 저장
        Letter letter = letterDomainService.findLetter(letterId);
        List<Participant> participants = participantDomainService.findAllParticipants(letterId);
        letterBoxDomainService.saveAllInParticipationLetterBox(participants, letter);
    }
}
