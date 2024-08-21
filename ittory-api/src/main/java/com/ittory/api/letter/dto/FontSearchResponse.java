package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Font;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FontSearchResponse {
    private Long id;
    private String name;

    public static FontSearchResponse from(Font font) {
        return FontSearchResponse.builder()
                .id(font.getId())
                .name(font.getName())
                .build();
    }
}