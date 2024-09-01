package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.repository.ElementRepository;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import com.ittory.domain.participant.domain.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ElementDomainService {

    private final ElementRepository elementRepository;

    public Element changeContent(Participant participant, Long elementId, String content) {
        Element element = elementRepository.findById(elementId)
                .orElseThrow(() -> new MemberNotFoundException(elementId));
        element.changeParticipant(participant);
        element.changeContent(content);
        return element;
    }

    public Integer countByParticipant(Participant participant) {
        return elementRepository.countByParticipant(participant);
    }

    public Page<Element> findAllByLetterId(Long letterId, Pageable pageable) {
        return elementRepository.findAllByLetterId(letterId, pageable);
    }
}
