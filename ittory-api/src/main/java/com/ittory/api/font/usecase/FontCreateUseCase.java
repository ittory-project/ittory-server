package com.ittory.api.font.usecase;

import com.ittory.api.font.dto.FontCreateRequest;
import com.ittory.api.font.dto.FontCreateResponse;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.service.FontDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FontCreateUseCase {

    private final FontDomainService fontDomainService;

    public FontCreateResponse execute(FontCreateRequest request) {
        Font font = fontDomainService.createFont(request.getName());
        return FontCreateResponse.from(font);
    }
}
