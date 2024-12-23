package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.CoverType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CoverTypeSearchResponse {

    private Long id;
    private String name;
    private String listImageUrl;
    private String selectImageUrl;
    private String notSelectImageUrl;
    private String editImageUrl;
    private String confirmImageUrl;
    private String outputBackgroundImageUrl;
    private String loadingBackgroundImageUrl;
    private String outputBoardImageUrl;
    private String listColor;

    public static CoverTypeSearchResponse from(CoverType coverType) {
        return CoverTypeSearchResponse.builder()
                .id(coverType.getId())
                .name(coverType.getName())
                .listImageUrl(coverType.getListImageUrl())
                .selectImageUrl(coverType.getSelectImageUrl())
                .notSelectImageUrl(coverType.getNotSelectImageUrl())
                .editImageUrl(coverType.getEditImageUrl())
                .confirmImageUrl(coverType.getConfirmImageUrl())
                .outputBackgroundImageUrl(coverType.getOutputBackgroundImageUrl())
                .loadingBackgroundImageUrl(coverType.getLoadingBackgroundImageUrl())
                .outputBoardImageUrl(coverType.getOutputBoardImageUrl())
                .listColor(coverType.getListColor())
                .build();
    }
}