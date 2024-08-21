package com.ittory.domain.participant.service;

import static com.ittory.domain.participant.enums.ParticipantStatus.EXITED;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.participant.repository.ParticipantRepository;
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

    @Transactional
    public void reorderParticipantsAfterLeave(Long letterId, Participant participant) {
        if (participant.getSequence() != null) {
            List<Participant> nextParticipants = participantRepository.findAllOrderNext(letterId,
                    participant.getSequence());

            nextParticipants.forEach(nextParticipant -> {
                Integer newSequence = nextParticipant.getSequence();
                nextParticipant.changeSequence(newSequence - 1);
            });

            participant.changeSequence(null);
            participantRepository.save(participant);
        }
    }

    @Transactional
    public Participant saveParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Transactional(readOnly = true)
    public List<Participant> findAllCurrentParticipantInOrder(Long letterId, boolean isAsc) {
        return participantRepository.findAllCurrentByIdWithMemberInOrder(letterId, isAsc);
    }

}
