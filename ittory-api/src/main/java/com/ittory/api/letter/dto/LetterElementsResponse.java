package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterElementsResponse {

    private Integer pageNumber;
    private Boolean hasNext;
    private List<ElementSimpleResponse> elements;

    public static LetterElementsResponse of(List<ElementSimpleResponse> elements, Page<Element> page) {
        return LetterElementsResponse.builder()
                .pageNumber(page.getNumber())
                .hasNext(page.hasNext())
                .elements(elements)
                .build();
    }

}
