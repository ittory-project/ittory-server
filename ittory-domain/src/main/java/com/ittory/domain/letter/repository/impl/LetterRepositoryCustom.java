package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Letter;

import java.time.LocalDate;

public interface LetterRepositoryCustom {
    long countByCreatedAt(LocalDate date);

    Letter findByIdWithAssociations(Long letterId);
}