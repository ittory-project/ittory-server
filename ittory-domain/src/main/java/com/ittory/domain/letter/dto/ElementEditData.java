package com.ittory.domain.letter.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementEditData {

    private Integer sequence;
    private String content;

    public static ElementEditData of(Integer sequence, String content) {
        return ElementEditData.builder()
                .sequence(sequence)
                .content(content)
                .build();
    }

}
