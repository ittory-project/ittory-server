package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.ElementSimpleResponse;
import com.ittory.api.letter.dto.LetterElementsResponse;
import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.service.ElementDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterElementsReadUseCase {

    private final ElementDomainService elementDomainService;

    @Transactional(readOnly = true)
    public LetterElementsResponse execute(Long letterId, Pageable pageable) {
        Page<Element> elementPage = elementDomainService.findPageByLetterId(letterId, pageable);

        List<ElementSimpleResponse> elements = elementPage.stream()
                .map(ElementSimpleResponse::from)
                .toList();

        return LetterElementsResponse.of(elements, elementPage);
    }

}
