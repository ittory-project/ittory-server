package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.CoverType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CoverTypeCreateResponse {
    private Long id;
    private String name;
    private String listImageUrl;
    private String selectImageUrl;
    private String editImageUrl;
    private String confirmImageUrl;
    private String outputBackgroundImageUrl;
    private String loadingBackgroundImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CoverTypeCreateResponse from(CoverType coverType) {
        return CoverTypeCreateResponse.builder()
                .id(coverType.getId())
                .name(coverType.getName())
                .listImageUrl(coverType.getListImageUrl())
                .selectImageUrl(coverType.getSelectImageUrl())
                .editImageUrl(coverType.getEditImageUrl())
                .confirmImageUrl(coverType.getConfirmImageUrl())
                .outputBackgroundImageUrl(coverType.getOutputBackgroundImageUrl())
                .loadingBackgroundImageUrl(coverType.getLoadingBackgroundImageUrl())
                .createdAt(coverType.getCreatedAt())
                .updatedAt(coverType.getUpdatedAt())
                .build();
    }
}