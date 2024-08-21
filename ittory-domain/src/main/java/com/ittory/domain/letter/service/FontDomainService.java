package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.exception.LetterException.FontNotFoundException;
import com.ittory.domain.letter.repository.FontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FontDomainService {

    private final FontRepository fontRepository;

    public Font createFont(String name) {
        Font font = Font.create(name);
        return fontRepository.save(font);
    }

    public Font getFontById(Long fontId) {
        return fontRepository.findById(fontId)
                .orElseThrow(() -> new FontNotFoundException(fontId));
    }
}
