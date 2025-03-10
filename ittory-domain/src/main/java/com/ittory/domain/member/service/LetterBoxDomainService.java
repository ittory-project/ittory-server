package com.ittory.domain.member.service;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.exception.MemberException.LetterBoxAlreadyStoredException;
import com.ittory.domain.member.repository.LetterBoxRepository;
import com.ittory.domain.participant.domain.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ittory.domain.member.enums.LetterBoxType.PARTICIPATION;
import static com.ittory.domain.member.enums.LetterBoxType.RECEIVE;

@Service
@RequiredArgsConstructor
public class LetterBoxDomainService {

    private final LetterBoxRepository letterBoxRepository;

    @Transactional(readOnly = true)
    public Boolean checkStorageStatus(Long letterId) {
        return letterBoxRepository.existsInLetterBox(letterId);
    }

    @Transactional
    public synchronized LetterBox saveInReceiveLetterBox(Member member, Letter letter) {
        Optional<LetterBox> existingLetterBox = letterBoxRepository.findReceivedByLetterIdWithLock(letter.getId());
        if (existingLetterBox.isEmpty()) {
            LetterBox letterBox = LetterBox.create(member, letter, RECEIVE);
            return letterBoxRepository.save(letterBox);
        } else {
            throw new LetterBoxAlreadyStoredException(letter.getId());
        }
    }

    @Transactional
    public void saveAllInParticipationLetterBox(List<Participant> participants, Letter letter) {
        for (Participant participant : participants) {
            LetterBox letterBox = LetterBox.create(participant.getMember(), letter, PARTICIPATION);
            letterBoxRepository.save(letterBox);
        }
    }

    @Transactional(readOnly = true)
    public Integer countParticipationLetterByMemberId(Long memberId) {
        return letterBoxRepository.countParticipationLetterByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Integer countReceiveLetterByMemberId(Long memberId) {
        return letterBoxRepository.countReceiveLetterByMemberId(memberId);
    }

    @Transactional
    public void deleteByMemberIdAndLetterId(Long memberId, Long letterId) {
        letterBoxRepository.deleteByMemberIdAndLetterId(memberId, letterId);
    }
}
