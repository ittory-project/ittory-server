package com.ittory.domain.member.service;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.LetterBoxType;
import com.ittory.domain.member.exception.MemberException.LetterBoxAlreadyStoredException;
import com.ittory.domain.member.repository.LetterBoxRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LetterBoxDomainService {

    private final LetterBoxRepository letterBoxRepository;

    @Transactional(readOnly = true)
    public Boolean checkStorageStatus(Long letterId) {
        return letterBoxRepository.existsInLetterBox(letterId);
    }

    @Transactional
    public synchronized LetterBox saveInLetterBox(Member member, Letter letter, LetterBoxType letterBoxType) {
        Optional<LetterBox> existingLetterBox = letterBoxRepository.findReceivedByLetterIdWithLock(letter.getId());
        if (existingLetterBox.isEmpty()) {
            LetterBox letterBox = LetterBox.create(member, letter, letterBoxType);
            return letterBoxRepository.save(letterBox);
        } else {
            throw new LetterBoxAlreadyStoredException(letter.getId());
        }
    }

}
