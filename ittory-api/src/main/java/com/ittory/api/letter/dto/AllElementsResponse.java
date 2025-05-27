package com.ittory.api.letter.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AllElementsResponse {

    List<ElementSimpleResponse> elements;

    public static AllElementsResponse from(List<ElementSimpleResponse> elements) {
        return AllElementsResponse.builder()
                .elements(elements)
                .build();
    }

}
