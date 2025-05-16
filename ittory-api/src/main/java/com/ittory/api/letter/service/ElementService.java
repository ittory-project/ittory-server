package com.ittory.api.letter.service;

import com.ittory.api.letter.dto.*;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.service.ElementDomainService;
import com.ittory.domain.letter.service.LetterDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementDomainService elementDomainService;
    private final LetterDomainService letterDomainService;


    public ElementImageResponse getElementImage(Long letterId, Integer sequence) {
        Element element = elementDomainService.findElementWithImage(letterId, sequence);
        return ElementImageResponse.from(element.getElementImage().getUrl());
    }

    @Transactional(readOnly = true)
    public LetterElementsResponse getAllElements(Long letterId, Pageable pageable) {
        Page<Element> elementPage = elementDomainService.findPageByLetterId(letterId, pageable);

        List<ElementSimpleResponse> elements = elementPage.stream()
                .map(ElementSimpleResponse::from)
                .toList();

        return LetterElementsResponse.of(elements, elementPage);
    }

    public LetterElementResponse updateElement(Long letterElementId, LetterElementRequest request) {
        Element element = elementDomainService.findById(letterElementId);
        letterDomainService.updateLetterElement(letterElementId, request.getContent());
        return new LetterElementResponse(letterElementId, element.getParticipant().getNickname(),
                element.getElementImage().getUrl(), request.getContent(), element.getSequence());
    }

    @Transactional(readOnly = true)
    public AllElementsResponse getCurrentElements(Long letterId) {
        List<ElementSimpleResponse> content = elementDomainService.findAllByLetterId(letterId).stream()
                .map(ElementSimpleResponse::from)
                .toList();

        return AllElementsResponse.from(content);
    }
}
