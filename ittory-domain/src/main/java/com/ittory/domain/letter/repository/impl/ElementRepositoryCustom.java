package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.participant.domain.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ElementRepositoryCustom {
    Page<Element> findPageByLetterId(Long letterId, Pageable pageable);

    Optional<Element> findByLetterIdAndSequence(Long letterId, Integer sequence);

    Element findByLetterIdAndSequenceWithImage(Long elementId, Integer sequence);

    List<Element> findAllByLetterId(Long letterId);

    Integer countNotNullByParticipant(Participant participant);
}
