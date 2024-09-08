package com.ittory.domain.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CoverTypeImages {

    private String imageUrl;
    private String simpleUrl;
    private String backgroundUrl;

    public static CoverTypeImages of(String imageUrl, String simpleUrl, String backgroundUrl) {
        return CoverTypeImages.builder()
                .imageUrl(imageUrl)
                .simpleUrl(simpleUrl)
                .backgroundUrl(backgroundUrl)
                .build();
    }

}
