package com.ittory.api.letter.usecase;

import com.ittory.api.participant.dto.ParticipantProfile;
import com.ittory.domain.participant.service.ParticipantDomainService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterParticipantReadUseCase {

    private final ParticipantDomainService participantDomainService;

    public List<ParticipantProfile> execute(Long letterId, String order) {
        Boolean isAscending = null;
        if (order != null && order.equals("sequence")) {
            isAscending = true;
        }
        return participantDomainService.findAllCurrentParticipantsOrderedBySequence(letterId, isAscending)
                .stream()
                .map(ParticipantProfile::from)
                .toList();
    }

}
