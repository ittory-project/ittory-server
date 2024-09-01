package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.*;
import com.ittory.domain.letter.exception.*;
import com.ittory.domain.letter.repository.*;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import com.ittory.domain.letter.repository.FontRepository;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import com.ittory.domain.member.service.MemberDomainService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LetterDomainService {

    private final LetterRepository letterRepository;
    private final CoverTypeRepository coverTypeRepository;
    private final FontRepository fontRepository;
    private final LetterElementRepository letterElementRepository;

    public Letter saveLetter(Long coverTypeId, Long fontId, Long receiverId, String receiverName,
                             LocalDateTime deliveryDate, String title, String coverPhotoUrl) {
        CoverType coverType = coverTypeRepository.findById(coverTypeId)
                .orElseThrow(() -> new LetterException.CoverTypeNotFoundException(coverTypeId));
        Font font = fontRepository.findById(fontId).orElseThrow(() -> new LetterException.FontNotFoundException(fontId));

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

    public void createLetterElements(Long letterId, int repeatCount) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException.LetterNotFoundException(letterId));

        List<Element> elements = IntStream.range(0, repeatCount)
                .mapToObj(i -> Element.create(letter, null, null,i, null))
                .collect(Collectors.toList());

        letterElementRepository.saveAll(elements);
    }

    public void updateLetterElement(Long letterElementId, String content) {
        Element element = letterElementRepository.findById(letterElementId)
                .orElseThrow(() -> new LetterElementException.LetterElementNotFoundException(letterElementId));

        element.ChangeContent(content);
        letterElementRepository.save(element);
    }

    public void deleteLetter(Long letterId) {
        // 편지 있는지  확인 후 삭제
        if (!letterRepository.existsById(letterId)) {
            throw new LetterException.LetterNotFoundException(letterId);
        }
        letterRepository.deleteById(letterId);
    }

    @Transactional(readOnly = true)
    public Letter findLetterById(Long letterId) {
        return letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterElementException.LetterElementNotFoundException(letterId));
    }

    @Transactional(readOnly = true)
    public List<Element> findElementsByLetterId(Long letterId) {
        return letterElementRepository.findByLetterId(letterId);
    }

    public Letter findLetter(Long letterId) {
        return letterRepository.findById(letterId).orElseThrow(() -> new MemberNotFoundException(letterId));
    }

}
