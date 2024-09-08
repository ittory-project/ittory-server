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
    private String imageUrl;
    private String simpleUrl;
    private String backgroundUrl;

    public static CoverTypeSearchResponse from(CoverType coverType) {
        return CoverTypeSearchResponse.builder()
                .id(coverType.getId())
                .name(coverType.getName())
                .imageUrl(coverType.getImageUrl())
                .simpleUrl(coverType.getSimpleUrl())
                .backgroundUrl(coverType.getBackgroundUrl())
                .build();
    }
}