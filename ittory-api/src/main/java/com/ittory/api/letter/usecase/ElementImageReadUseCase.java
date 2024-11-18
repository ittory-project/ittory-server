package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.ElementImageResponse;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.service.ElementDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElementImageReadUseCase {

    private final ElementDomainService elementDomainService;

    public ElementImageResponse execute(Long letterId, Integer sequence) {
        Element element = elementDomainService.findElementWithImage(letterId, sequence);
        return ElementImageResponse.from(element.getElementImage().getUrl());
    }

}
