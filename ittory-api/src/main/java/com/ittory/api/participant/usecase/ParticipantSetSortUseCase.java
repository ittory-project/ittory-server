package com.ittory.api.participant.usecase;

import com.ittory.api.member.dto.MemberLetterProfile;
import com.ittory.api.participant.dto.ParticipantSortResponse;
import com.ittory.api.participant.dto.SortRandomRequest;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.service.ParticipantDomainService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantSetSortUseCase {

    private final ParticipantDomainService participantDomainService;

    public ParticipantSortResponse execute(SortRandomRequest request) {
        List<Participant> participants = getShuffledParticipants(request);
        List<Participant> savedParticipants = givenSort(participants);
        List<MemberLetterProfile> profiles = savedParticipants.stream().map(MemberLetterProfile::from).toList();
        return ParticipantSortResponse.of(request.getLetterId(), profiles);
    }

    private List<Participant> getShuffledParticipants(SortRandomRequest request) {
        List<Participant> participants
                = participantDomainService.findAllCurrentParticipant(request.getParticipantIds());
        Collections.shuffle(participants);
        return participants;
    }

    private List<Participant> givenSort(List<Participant> participants) {
        for (int sort = 0; sort < participants.size(); sort++) {
            participants.get(sort).changeSort(sort + 1);
        }
        return participantDomainService.saveAllParticipant(participants);
    }

}
