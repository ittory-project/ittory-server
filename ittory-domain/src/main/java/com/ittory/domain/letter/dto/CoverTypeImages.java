package com.ittory.domain.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CoverTypeImages {

    private String listImageUrl;
    private String selectImageUrl;
    private String editImageUrl;
    private String confirmImageUrl;
    private String outputBackgroundImageUrl;
    private String loadingBackgroundImageUrl;

    public static CoverTypeImages of(String listImageUrl, String selectImageUrl, String editImageUrl,
                                     String confirmImageUrl, String outputBackgroundImageUrl, String loadingBackgroundImageUrl) {
        return CoverTypeImages.builder()
                .listImageUrl(listImageUrl)
                .selectImageUrl(selectImageUrl)
                .editImageUrl(editImageUrl)
                .confirmImageUrl(confirmImageUrl)
                .outputBackgroundImageUrl(outputBackgroundImageUrl)
                .loadingBackgroundImageUrl(loadingBackgroundImageUrl)
                .build();
    }

}
