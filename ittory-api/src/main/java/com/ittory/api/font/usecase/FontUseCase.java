package com.ittory.api.font.usecase;

import com.ittory.api.font.dto.FontCreateRequest;
import com.ittory.api.font.dto.FontCreateResponse;
import com.ittory.api.font.dto.FontSearchResponse;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.repository.FontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FontUseCase {

    private final FontRepository fontRepository;

    public FontCreateResponse createFont(FontCreateRequest request) {
        Font font = Font.create(request.getName());
        font = fontRepository.save(font);
        return FontCreateResponse.from(font);
    }

    public FontSearchResponse getFontById(Long fontId) {
        Font font = fontRepository.findById(fontId).orElseThrow(IllegalArgumentException::new);
        return FontSearchResponse.from(font);
    }
}
