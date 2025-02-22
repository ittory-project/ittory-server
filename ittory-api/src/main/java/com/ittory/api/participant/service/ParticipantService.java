package com.ittory.api.participant.service;

import com.ittory.api.participant.dto.*;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.service.ParticipantDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantDomainService participantDomainService;

    public synchronized ParticipantNicknameResponse updateParticipantNickname(Long memberId, Long letterId, ParticipantNicknameRequest request) {
        Boolean isDuplicate = participantDomainService.checkNicknameDuplication(letterId, request.getNickname());

        String nickname = null;
        if (!isDuplicate) {
            Participant participant = participantDomainService.findParticipant(letterId, memberId);
            participant.changeNickname(request.getNickname());
            participantDomainService.saveParticipant(participant);
            nickname = request.getNickname();
        }

        return ParticipantNicknameResponse.of(!isDuplicate, nickname);
    }

    public void deleteParticipantNickname(Long memberId, Long letterId) {
        Participant participant = participantDomainService.findParticipant(letterId, memberId);
        participant.changeNickname(null);
        participantDomainService.saveParticipant(participant);
    }

    public NicknameDuplicationResponse checkDuplicationNickname(Long letterId, String nickname) {
        Boolean isDuplicate = participantDomainService.checkNicknameDuplication(letterId, nickname);
        return com.ittory.api.participant.dto.NicknameDuplicationResponse.from(isDuplicate);
    }

    public ParticipantSortResponse getParticipantWithRandomSort(SortRandomRequest request) {
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
