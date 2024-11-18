package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.FontSearchResponse;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.service.FontDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FontReadUseCase {

    private final FontDomainService fontDomainService;

    public FontSearchResponse execute(Long fontId) {
        Font font = fontDomainService.getFontById(fontId);
        return FontSearchResponse.from(font);
    }
}
