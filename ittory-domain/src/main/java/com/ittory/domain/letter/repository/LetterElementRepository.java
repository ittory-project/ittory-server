package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.repository.impl.LetterElementRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterElementRepository extends JpaRepository<Element, Long>, LetterElementRepositoryCustom {
    List<Element> findByLetterId(Long letterId);
}
