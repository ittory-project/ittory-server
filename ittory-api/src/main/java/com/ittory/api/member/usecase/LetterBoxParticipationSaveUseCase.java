package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.LetterBoxParticipationRequest;
import com.ittory.domain.letter.domain.Letter;
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
public class LetterBoxParticipationSaveUseCase {

    private final LetterDomainService letterDomainService;
    private final ParticipantDomainService participantDomainService;
    private final LetterBoxDomainService letterBoxDomainService;


    public void execute(LetterBoxParticipationRequest request) {
        Letter letter = letterDomainService.findLetter(request.getLetterId());
        List<Participant> participants = participantDomainService.findAllParticipants(request.getLetterId());
        letterBoxDomainService.saveAllInParticipationLetterBox(participants, letter);
        participantDomainService.updateAllStatusToEnd(letter.getId());
        changeParticipantStatus(participants);
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
