package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElementRepositoryCustom {
    Page<Element> findAllByLetterId(Long letterId, Pageable pageable);
}
