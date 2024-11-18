package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElementRepositoryCustom {
    Page<Element> findAllByLetterId(Long letterId, Pageable pageable);

    Optional<Element> findByLetterIdAndSequence(Long letterId, Integer sequence);

    Element findByLetterIdAndSequenceWithImage(Long elementId, Integer sequence);
}
