package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.dto.ElementEditData;
import com.ittory.domain.letter.exception.LetterException.ElementNotFoundException;
import com.ittory.domain.letter.repository.ElementRepository;
import com.ittory.domain.participant.domain.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementDomainService {

    private final ElementRepository elementRepository;

    @Transactional
    public Element changeContent(Participant participant, ElementEditData editData) {
        Element element = elementRepository.findById(editData.getElementId()).orElseThrow(ElementNotFoundException::new);
        element.changeParticipant(participant);
        element.changeContent(editData.getContent());
        return element;
    }

    @Transactional(readOnly = true)
    public Integer countByParticipant(Participant participant) {
        return elementRepository.countNotNullByParticipant(participant);
    }

    @Transactional(readOnly = true)
    public Page<Element> findPageByLetterId(Long letterId, Pageable pageable) {
        return elementRepository.findPageByLetterId(letterId, pageable);
    }

    @Transactional(readOnly = true)
    public Element findElementWithImage(Long letterId, Integer sequence) {
        return elementRepository.findByLetterIdAndSequenceWithImage(letterId, sequence);
    }

    public void deleteAllByLetterId(Long letterId) {
        elementRepository.deleteAllByLetterId(letterId);
    }

    @Transactional(readOnly = true)
    public Element findById(Long letterElementId) {
        return elementRepository.findById(letterElementId).orElseThrow(ElementNotFoundException::new);
    }

    public List<Element> findAllByLetterId(Long letterId) {
        return elementRepository.findAllByLetterId(letterId);
    }

    public void updateStartTimeAndWriter(Long letterId, Integer sequence, Participant participant, LocalDateTime starTime) {
        Element element = elementRepository.findByLetterIdAndSequence(letterId, sequence).orElseThrow(ElementNotFoundException::new);
        element.changeParticipant(participant);
        element.changeStartTime(starTime);
    }

    public Element findNextElement(Long letterId) {
        return elementRepository.findNextElement(letterId).orElse(null);
    }

    @Transactional
    public void changeProcessDataByLetterId(Long letterId, LocalDateTime nowTime, Participant nextParticipant) {
        elementRepository.changeProcessDataByLetterId(letterId, nowTime, nextParticipant);
    }
}
