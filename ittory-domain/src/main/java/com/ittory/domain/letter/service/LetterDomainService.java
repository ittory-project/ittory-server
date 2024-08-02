package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.exception.CoverTypeException;
import com.ittory.domain.letter.exception.FontException;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import com.ittory.domain.letter.repository.FontRepository;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LetterDomainService {

    private final LetterRepository letterRepository;
    private final CoverTypeRepository coverTypeRepository;
    private final FontRepository fontRepository;
    private final MemberDomainService memberDomainService;

    public Letter saveLetter(Long coverTypeId, Long fontId, Long receiverId, String receiverName, LocalDateTime deliveryDate, String title, String coverPhotoUrl) {
        CoverType coverType = coverTypeRepository.findById(coverTypeId) .orElseThrow(() -> new CoverTypeException.CoverTypeNotFoundException(coverTypeId));
        Font font = fontRepository.findById(fontId).orElseThrow(() -> new FontException.FontNotFoundException(fontId));


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
}
