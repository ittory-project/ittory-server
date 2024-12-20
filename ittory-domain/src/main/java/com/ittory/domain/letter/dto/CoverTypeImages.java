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
    private String notSelectImageUrl;
    private String editImageUrl;
    private String confirmImageUrl;
    private String outputBackgroundImageUrl;
    private String loadingBackgroundImageUrl;

    public static CoverTypeImages of(String listImageUrl, String selectImageUrl, String notSelectImageUrl,
                                     String editImageUrl, String confirmImageUrl, String outputBackgroundImageUrl,
                                     String loadingBackgroundImageUrl) {
        return CoverTypeImages.builder()
                .listImageUrl(listImageUrl)
                .selectImageUrl(selectImageUrl)
                .notSelectImageUrl(notSelectImageUrl)
                .editImageUrl(editImageUrl)
                .confirmImageUrl(confirmImageUrl)
                .outputBackgroundImageUrl(outputBackgroundImageUrl)
                .loadingBackgroundImageUrl(loadingBackgroundImageUrl)
                .build();
    }

}
