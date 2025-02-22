package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;

import java.util.List;

public interface LetterElementRepositoryCustom {
    void saveAllInBatch(List<Element> elements);
}
