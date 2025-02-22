package com.ittory.api.letter.service;

import com.ittory.api.letter.dto.FontCreateRequest;
import com.ittory.api.letter.dto.FontCreateResponse;
import com.ittory.api.letter.dto.FontSearchResponse;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.service.FontDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FontService {

    private final FontDomainService fontDomainService;

    public List<FontSearchResponse> getAllFonts() {
        return fontDomainService.getAllFont().stream()
                .map(FontSearchResponse::from)
                .toList();
    }

    public FontCreateResponse createFont(FontCreateRequest request) {
        Font font = fontDomainService.createFont(request.getName(), request.getValue());
        return FontCreateResponse.from(font);
    }

    public FontSearchResponse getFont(Long fontId) {
        Font font = fontDomainService.getFontById(fontId);
        return FontSearchResponse.from(font);
    }
}
