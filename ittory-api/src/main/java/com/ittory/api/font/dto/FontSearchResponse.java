package com.ittory.api.font.dto;

import com.ittory.domain.letter.domain.Font;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
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