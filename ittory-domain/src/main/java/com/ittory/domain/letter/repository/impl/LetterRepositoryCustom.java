package com.ittory.domain.letter.repository.impl;

import java.time.LocalDate;

public interface LetterRepositoryCustom {
    long countByCreatedAt(LocalDate date);
}