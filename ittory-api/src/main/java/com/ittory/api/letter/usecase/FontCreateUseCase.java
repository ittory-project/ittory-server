package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.FontCreateRequest;
import com.ittory.api.letter.dto.FontCreateResponse;
import com.ittory.domain.letter.domain.Font;
import com.ittory.domain.letter.service.FontDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FontCreateUseCase {

    private final FontDomainService fontDomainService;

    public FontCreateResponse execute(FontCreateRequest request) {
        Font font = fontDomainService.createFont(request.getName(), request.getValue());
        return FontCreateResponse.from(font);
    }
}
