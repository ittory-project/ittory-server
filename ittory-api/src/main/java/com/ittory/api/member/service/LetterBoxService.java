package com.ittory.api.member.service;

import com.ittory.api.member.dto.LetterBoxParticipationRequest;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ittory.domain.participant.enums.ParticipantStatus.COMPLETED;
import static com.ittory.domain.participant.enums.ParticipantStatus.PROGRESS;

@Service
@RequiredArgsConstructor
public class LetterBoxService {

    private final LetterBoxDomainService letterBoxDomainService;
    private final LetterDomainService letterDomainService;
    private final ParticipantDomainService participantDomainService;

    public void deleteLetterInLetterBox(Long memberId, Long letterId) {
        letterBoxDomainService.deleteByMemberIdAndLetterId(memberId, letterId);
    }

    public void saveInParticipationLetterBox(LetterBoxParticipationRequest request) {
        Letter letter = letterDomainService.findLetter(request.getLetterId());
        List<Participant> participants = participantDomainService.findAllParticipants(request.getLetterId());
        letterBoxDomainService.saveAllInParticipationLetterBox(participants, letter);
        participantDomainService.updateAllStatusToEnd(letter.getId());
        changeParticipantStatus(participants);
        letterDomainService.updateLetterStatus(letter.getId(), LetterStatus.COMPLETED);
    }

    private void changeParticipantStatus(List<Participant> participants) {
        for (Participant participant : participants) {
            if (participant.getParticipantStatus().equals(PROGRESS)) {
                participant.changeParticipantStatus(COMPLETED);
            }
        }

        participantDomainService.saveAllParticipant(participants);
    }

}
