package com.ittory.socket.usecase;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import com.ittory.socket.dto.DeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LetterDeleteUseCase {

    private final ParticipantDomainService participantDomainService;

    public DeleteResponse execute(Long memberId, Long letterId) {
        Participant manager = participantDomainService.findManagerByLetterId(letterId);
        if (!manager.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("Manager did not match");
        }
        participantDomainService.updateAllStatusToDelete(letterId);
        return DeleteResponse.from(letterId);
    }
}
