package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.letter.repository.LetterElementRepository;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LetterElementDomainService {

    private final LetterElementRepository letterElementRepository;

    public LetterElement changeContent(Participant participant, Long elementId, String content) {
        LetterElement element = letterElementRepository.findById(elementId)
                .orElseThrow(() -> new MemberNotFoundException(elementId));
        element.changeParticipant(participant);
        element.changeContent(content);
        return element;
    }

    public Integer countByParticipant(Participant participant) {
        return letterElementRepository.countByParticipant(participant);
    }
}
