package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.exception.LetterException.FontNotFoundException;
import com.ittory.domain.letter.repository.FontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FontDomainService {

    private final FontRepository fontRepository;

    @Transactional
    public Font createFont(String name, String value) {
        Font font = Font.create(name, value);
        return fontRepository.save(font);
    }

    @Transactional(readOnly = true)
    public Font getFontById(Long fontId) {
        return fontRepository.findById(fontId).orElseThrow(() -> new FontNotFoundException(fontId));
    }

    @Transactional(readOnly = true)
    public List<Font> getAllFont() {
        return fontRepository.findAll();
    }

}
