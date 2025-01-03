package com.ittory.api.participant.usecase;

import com.ittory.api.participant.dto.ParticipantProfile;
import com.ittory.api.participant.dto.ParticipantSortResponse;
import com.ittory.api.participant.dto.SortRandomRequest;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantSetSortUseCase {

    private final ParticipantDomainService participantDomainService;

    public ParticipantSortResponse execute(SortRandomRequest request) {
        List<Participant> participants = shuffleParticipants(request.getLetterId());
        List<Participant> savedParticipants = giveSort(participants);
        List<ParticipantProfile> profiles = convertToMemberLetterProfiles(savedParticipants);
        return ParticipantSortResponse.from(profiles);
    }

    private List<Participant> shuffleParticipants(Long letterId) {
        List<Participant> participants = participantDomainService.findAllCurrentParticipantsOrderedBySequence(letterId,
                null);
        Collections.shuffle(participants);
        return participants;
    }

    private List<Participant> giveSort(List<Participant> participants) {
        for (int sequence = 0; sequence < participants.size(); sequence++) {
            participants.get(sequence).changeSequence(sequence + 1);
        }
        return participantDomainService.saveAllParticipant(participants);
    }

    private List<ParticipantProfile> convertToMemberLetterProfiles(List<Participant> savedParticipants) {
        return savedParticipants.stream().map(ParticipantProfile::from).toList();
    }

}
