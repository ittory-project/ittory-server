package com.ittory.domain.participant.service;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ittory.common.constant.GlobalConstant.PARTICIPANTS_SIZE;
import static com.ittory.domain.participant.enums.ParticipantStatus.GUEST;

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
    public List<Participant> findAllCurrentParticipantsOrderedBySequence(Long letterId, Boolean isAscending) {
        return participantRepository.findCurrentParticipantsByLetterIdOrdered(letterId, isAscending);
    }

    @Transactional
    public List<Participant> saveAllParticipant(List<Participant> participants) {
        return participantRepository.saveAll(participants);
    }

    @Transactional
    public void exitParticipant(Participant participant) {
        participant.changeParticipantStatus(GUEST);
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
    public List<Participant> findAllParticipants(Long letterId) {
        return participantRepository.findAllParticipantsWithMember(letterId);
    }

    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplication(Long letterId, String nickname) {
        Participant participant = participantRepository.findEnterByNickname(letterId, nickname);
        return participant != null;
    }

    @Transactional(readOnly = true)
    public Boolean getEnterStatus(Long letterId) {
        Integer participantCount = participantRepository.countEnterParticipantByLetterId(letterId);
        return participantCount < PARTICIPANTS_SIZE;
    }

    @Transactional(readOnly = true)
    public Integer countProgressByLetterId(Long letterId) {
        return participantRepository.countProgressByLetterId(letterId);
    }

    @Transactional(readOnly = true)
    public Participant findEnterParticipantOrNull(Long letterId, Long memberId) {
        return participantRepository.findEnterParticipantByLetterIdAndMemberId(letterId, memberId).orElse(null);
    }

    @Transactional(readOnly = true)
    public Participant findManagerByLetterId(Long letterId) {
        return participantRepository.findManagerByLetterId(letterId);
    }

    @Transactional
    public void updateAllStatusToStart(Long letterId) {
        participantRepository.updateAllStatusToStart(letterId);
    }

    @Transactional
    public void updateAllStatusToEnd(Long letterId) {
        participantRepository.updateAllStatusToEnd(letterId);
    }

    @Transactional
    public void updateAllStatusToDelete(Long letterId) {
        participantRepository.updateAllStatusToDelete(letterId);
    }

    @Transactional(readOnly = true)
    public Participant findParticipantOrNull(Long letterId, Long memberId) {
        return participantRepository.findByLetterIdAndMemberId(letterId, memberId).orElse(null);
    }
}
