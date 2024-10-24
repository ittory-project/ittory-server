package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.*;
import com.ittory.domain.letter.exception.LetterException;
import com.ittory.domain.letter.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
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
    private final ElementImageRepository elementImageRepository;

    @Transactional
    public Letter saveLetter(Long coverTypeId, Long fontId, Long receiverId, String receiverName,
                             LocalDateTime deliveryDate, String title, String coverPhotoUrl) {
        CoverType coverType = coverTypeRepository.findById(coverTypeId)
                .orElseThrow(() -> new LetterException.CoverTypeNotFoundException(coverTypeId));
        Font font = fontRepository.findById(fontId)
                .orElseThrow(() -> new LetterException.FontNotFoundException(fontId));

        Letter letter = Letter.create(
                coverType,
                font,
                receiverName,
                deliveryDate,
                title,
                coverPhotoUrl,
                null
        );

        return letterRepository.save(letter);
    }

    @Transactional
    public void createLetterElements(Long letterId, int repeatCount) {
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterException.LetterNotFoundException(letterId));

        List<ElementImage> elementImages = elementImageRepository.findAll();

        // 요청된 반복횟수가 전체 요소 이미지의 개수보다 적을 때만 정상작동
        if (elementImages.size() < repeatCount) {
            throw new LetterException.RepeatCountTooManyException();
        }

        Collections.shuffle(elementImages);
        List<Element> elements = IntStream.range(0, repeatCount)
                .mapToObj(i -> Element.create(letter, null, elementImages.get(i), i, null))
                .collect(Collectors.toList());

        letterElementRepository.saveAll(elements);
    }

    @Transactional
    public void updateLetterElement(Long letterElementId, String content) {
        Element element = letterElementRepository.findById(letterElementId)
                .orElseThrow(() -> new LetterException.LetterNotFoundException(letterElementId));

        element.changeContent(content);
        letterElementRepository.save(element);
    }

    @Transactional
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
                .orElseThrow(() -> new LetterException.LetterNotFoundException(letterId));
    }

    @Transactional(readOnly = true)
    public List<Element> findElementsByLetterId(Long letterId) {
        return letterElementRepository.findByLetterId(letterId);
    }

    @Transactional(readOnly = true)
    public Letter findLetter(Long letterId) {
        return letterRepository.findById(letterId).orElseThrow(() -> new LetterException.LetterNotFoundException(letterId));
    }

    @Transactional
    public void changeRepeatCount(Letter letter, int repeatCount) {
        letter.changeRepeatCount(repeatCount);
        letterRepository.save(letter);
    }

}
