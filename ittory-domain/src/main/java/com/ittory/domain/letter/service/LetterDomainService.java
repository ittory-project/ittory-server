package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.exception.LetterException.CoverTypeNotFoundException;
import com.ittory.domain.letter.exception.LetterException.FontNotFoundException;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import com.ittory.domain.letter.repository.FontRepository;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import com.ittory.domain.member.service.MemberDomainService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterDomainService {

    private final LetterRepository letterRepository;
    private final CoverTypeRepository coverTypeRepository;
    private final FontRepository fontRepository;
    private final MemberDomainService memberDomainService;

    public Letter saveLetter(Long coverTypeId, Long fontId, Long receiverId, String receiverName,
                             LocalDateTime deliveryDate, String title, String coverPhotoUrl) {
        CoverType coverType = coverTypeRepository.findById(coverTypeId)
                .orElseThrow(() -> new CoverTypeNotFoundException(coverTypeId));
        Font font = fontRepository.findById(fontId).orElseThrow(() -> new FontNotFoundException(fontId));

        Letter letter = Letter.create(
                coverType,
                font,
                receiverName,
                deliveryDate,
                title,
                coverPhotoUrl
        );

        return letterRepository.save(letter);
    }

    public Letter findLetter(Long letterId) {
        return letterRepository.findById(letterId).orElseThrow(() -> new MemberNotFoundException(letterId));
    }

}
