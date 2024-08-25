package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.*;
import com.ittory.domain.letter.exception.*;
import com.ittory.domain.letter.repository.*;
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

    public Letter saveLetter(Long coverTypeId, Long fontId, Long receiverId, String receiverName, LocalDateTime deliveryDate, String title, String coverPhotoUrl) {
        CoverType coverType = coverTypeRepository.findById(coverTypeId)
                .orElseThrow(() -> new CoverTypeException.CoverTypeNotFoundException(coverTypeId));
        Font font = fontRepository.findById(fontId)
                .orElseThrow(() -> new FontException.FontNotFoundException(fontId));

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

        List<LetterElement> elements = IntStream.range(0, repeatCount)
                .mapToObj(i -> LetterElement.create(letter, null, i, null))
                .collect(Collectors.toList());

        letterElementRepository.saveAll(elements);
    }

    public void updateLetterElement(Long letterElementId, String content) {
        LetterElement letterElement = letterElementRepository.findById(letterElementId)
                .orElseThrow(() -> new LetterElementException.LetterElementNotFoundException(letterElementId));

        letterElement.ChangeContent(content);
        letterElementRepository.save(letterElement);
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
    public List<LetterElement> findElementsByLetterId(Long letterId) {
        return letterElementRepository.findByLetterId(letterId);
    }
}
