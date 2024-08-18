package com.ittory.domain.member.service;

import static com.ittory.domain.member.enums.ParticipantStatus.EXITED;

import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.member.repository.ParticipantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantDomainService {

    private final ParticipantRepository participantRepository;

    @Transactional(readOnly = true)
    public Participant findParticipant(Long letterId, Long memberId) {
        return participantRepository.findByLetterIdAndMemberId(letterId, memberId)
                .orElseThrow(() -> new ParticipantNotFoundException(letterId, memberId));
    }

    @Transactional(readOnly = true)
    public List<Participant> findAllCurrentParticipant(Long letterId) {
        return participantRepository.findAllCurrentByIdWithMember(letterId);
    }

    @Transactional
    public List<Participant> saveAllParticipant(List<Participant> participants) {
        return participantRepository.saveAll(participants);
    }

    @Transactional
    public void exitParticipant(Participant participant) {
        participant.changeParticipantStatus(EXITED);
        participantRepository.save(participant);
    }

    @Transactional
    public void deleteParticipant(Participant participant) {
        participantRepository.deleteById(participant.getId());
    }
}
