package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.LetterElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterElementRepository extends JpaRepository<LetterElement, Long> {
    List<LetterElement> findByLetterId(Long letterId);
}
