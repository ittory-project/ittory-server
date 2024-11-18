package com.ittory.api.letter.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementImageResponse {

    private String elementImageUrl;

    public static ElementImageResponse from(String imageUrl) {
        return ElementImageResponse.builder()
                .elementImageUrl(imageUrl)
                .build();
    }

}
