package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.FontSearchResponse;
import com.ittory.domain.letter.service.FontDomainService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FontAllReadUseCase {

    private final FontDomainService fontDomainService;

    public List<FontSearchResponse> execute() {
        return fontDomainService.getAllFont().stream()
                .map(FontSearchResponse::from)
                .toList();
    }

}
