package com.ittory.domain.letter.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementEditData {

    private Long elementId;
    private String content;

    public static ElementEditData of(Long elementId, String content) {
        return ElementEditData.builder()
                .elementId(elementId)
                .content(content)
                .build();
    }

}
