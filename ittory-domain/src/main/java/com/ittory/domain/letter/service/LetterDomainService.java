package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.*;
import com.ittory.domain.letter.exception.LetterException;
import com.ittory.domain.letter.repository.*;
import com.ittory.domain.participant.repository.ParticipantRepository;
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
    private final ParticipantRepository participantRepository;

    @Transactional
    public Letter saveLetter(Long coverTypeId, Long fontId, String receiverName,
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
    public void createLetterElements(Letter letter, int repeatCount) {
        Integer participantCount = participantRepository.countProgressByLetterId(letter.getId());
        int totalCount = participantCount * repeatCount;

        List<ElementImage> elementImages = elementImageRepository.findAll();

        // 요청된 반복횟수가 전체 요소 이미지의 개수보다 적을 때만 정상작동
        if (elementImages.size() < totalCount) {
            throw new LetterException.RepeatCountTooManyException();
        }

        Collections.shuffle(elementImages);
        List<Element> elements = IntStream.range(1, totalCount + 1)
                .mapToObj(sequence -> Element.create(letter, null, elementImages.get(sequence), sequence, null))
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
